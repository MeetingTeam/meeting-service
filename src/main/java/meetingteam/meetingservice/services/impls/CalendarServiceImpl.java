package meetingteam.meetingservice.services.impls;

import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.exceptions.BadRequestException;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.commonlibrary.utils.DateTimeUtil;
import meetingteam.meetingservice.dtos.Calendar.CalendarDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;
import meetingteam.meetingservice.repositories.MeetingRepository;
import meetingteam.meetingservice.services.CalendarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {
    private final MeetingRepository meetingRepo;
    private final ModelMapper modelMapper;

    @Override
    public void addToCalendar(String meetingId, boolean isAdded) {
        String userId= AuthUtil.getUserId();
        var meeting=meetingRepo.findById(meetingId).orElseThrow(()->new BadRequestException("Meeting not found"));

        if(isAdded) meeting.getCalendarUserIds().add(userId);
        else meeting.getCalendarUserIds().remove(userId);
        meetingRepo.save(meeting);
    }

    @Override
    public CalendarDto getMeetingsOfWeek(Integer week) {
        String userId= AuthUtil.getUserId();
        var weekRange= DateTimeUtil.getWeekRange(week);

        var rawMeetings=meetingRepo.getCalendarMeetings(userId);
        var meetings=rawMeetings.stream().filter(m->{
            if(m.getScheduledDaysOfWeek()==null||m.getScheduledDaysOfWeek().isEmpty()){
                return m.getStartDate().isAfter(weekRange.get(0));
            }
            else{
                if(m.getStartDate().isAfter(weekRange.get(1))) return false;
                else if(m.getEndDate()!=null && !m.getEndDate().isBefore(weekRange.get(0)))
                    return false;
                return true;
            }
        }).toList();
        var resMeetingDtos= meetings.stream()
                .map(meeting->modelMapper.map(meeting, ResMeetingDto.class))
                .toList();
        return new CalendarDto(resMeetingDtos,weekRange);
    }
}
