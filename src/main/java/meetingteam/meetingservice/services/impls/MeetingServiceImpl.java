package meetingteam.meetingservice.services.impls;

import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.exceptions.BadRequestException;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.commonlibrary.utils.PageUtil;
import meetingteam.meetingservice.dtos.Meeting.CreateMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.DeleteMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.UpdateMeetingDto;
import meetingteam.meetingservice.models.Meeting;
import meetingteam.meetingservice.models.Reaction;
import meetingteam.meetingservice.repositories.MeetingRepository;
import meetingteam.meetingservice.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepo;
    private final TeamService teamService;
    private final WebsocketService websocketService;
    private final ModelMapper modelMapper;

    @Override
    public void createMeeting(CreateMeetingDto meetingDto) {
        String userId= AuthUtil.getUserId();

        if(!teamService.isMemberOfTeam(userId, meetingDto.getTeamId(),meetingDto.getChannelId()))
            throw new AccessDeniedException("You don't have the permission to add meeting to this channel");

        Meeting meeting= modelMapper.map(meetingDto, Meeting.class);
        if(meeting.getScheduledTime()!=null){
            if(meeting.getStartDate()==null) meeting.setStartDate(LocalDate.now());
        }
        meeting.setCreatorId(userId);
        meeting.setCalendarUserIds(new HashSet());
        meeting.setIsCanceled(false);
        meeting.setCreatedAt(LocalDateTime.now());
        var savedMeeting=meetingRepo.save(meeting);

        var resMeetingDto= modelMapper.map(savedMeeting, ResMeetingDto.class);
        websocketService.addOrUpdateMeeting(savedMeeting.getTeamId(), resMeetingDto);
    }

    @Override
    public void updateMeeting(UpdateMeetingDto meetingDto) {
        var meeting= meetingRepo.findById(meetingDto.getId()).orElseThrow(()->new BadRequestException("Meeting not found"));

        if(meetingDto.getTitle()!=null) meeting.setTitle(meetingDto.getTitle());
        if(meetingDto.getStartDate()!=null) meeting.setStartDate(meetingDto.getStartDate());
        if(meetingDto.getEndDate()!=null) meeting.setEndDate(meetingDto.getEndDate());
        if(meetingDto.getScheduledTime()!=null) meeting.setScheduledTime(meetingDto.getScheduledTime());
        if(meetingDto.getScheduledDaysOfWeek()!=null) meeting.setScheduledDaysOfWeek(meetingDto.getScheduledDaysOfWeek());

        meetingRepo.save(meeting);

        var resMeetingDto= modelMapper.map(meeting, ResMeetingDto.class);
        websocketService.addOrUpdateMeeting(meeting.getTeamId(), resMeetingDto);
    }

    @Override
    public void reactMeeting(String meetingId, String emojiCode) {
        var meeting=meetingRepo.findById(meetingId).orElseThrow(()->new BadRequestException("MeetingId "+meetingId+" does not exists"));

        Reaction reaction= new Reaction(AuthUtil.getUserId(), emojiCode);
        var reactions=meeting.getReactions();
        if(reactions==null) reactions=new ArrayList();
        int i=0;
        for(;i<reactions.size();i++) {
            if(reaction.getUserId().equals(reactions.get(i).getUserId())) {
                if(reaction.getEmojiCode()==null) reactions.remove(i);
                else reactions.set(i, reaction);
                break;
            }
        }
        if(i==reactions.size()&&reaction.getEmojiCode()!=null)
            reactions.add(reaction);
        meeting.setReactions(reactions);

        meetingRepo.save(meeting);

        var resMeetingDto= modelMapper.map(meeting, ResMeetingDto.class);
        websocketService.addOrUpdateMeeting(meeting.getTeamId(), resMeetingDto);
    }

    @Override
    public void cancelMeeting(String meetingId, boolean isCanceled) {
        String userId= AuthUtil.getUserId();
        var meeting=meetingRepo.findById(meetingId).orElseThrow(()->new BadRequestException("MeetingId "+meetingId+" does not exists"));
        if(!userId.equals(meeting.getCreatorId()))
            throw new AccessDeniedException("You do not have permission to update this meeting");
        if(meeting.getIsCanceled()==isCanceled) return;

        meeting.setIsCanceled(isCanceled);
        meetingRepo.save(meeting);

        var resMeetingDto= modelMapper.map(meeting, ResMeetingDto.class);
        websocketService.addOrUpdateMeeting(meeting.getTeamId(), resMeetingDto);
    }

    @Override
    public void deleteMeeting(String meetingId) {
        String userId= AuthUtil.getUserId();

        var meeting=meetingRepo.findById(meetingId).orElseThrow(()->new BadRequestException("Meeting not found"));
        if(!userId.equals(meeting.getCreatorId()))
            throw new AccessDeniedException("You do not have permission to delete this meeting");

        meetingRepo.deleteById(meetingId);

        websocketService.deleteMeeting(meeting.getTeamId(), 
                new DeleteMeetingDto(meeting.getChannelId(), meetingId));
    }

    @Override
    public void deleteMeetingsByChannelId(String channelId) {
        meetingRepo.deleteByChannelId(channelId);
    }

    @Override
    public List<ResMeetingDto> getVideoChannelMeetings(String channelId, Integer receivedMeetingNum) {
        int pageSize= PageUtil.findBestPageSize(receivedMeetingNum);
        var meetings= meetingRepo.getMeetingsByChannelId(channelId, PageRequest.of(receivedMeetingNum/pageSize, pageSize));
        Collections.reverse(meetings);
        return meetings.stream()
                .map(meeting-> modelMapper.map(meeting, ResMeetingDto.class))
                .toList();
    }
}
