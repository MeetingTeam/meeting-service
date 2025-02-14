package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.Meeting.CreateMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.UpdateMeetingDto;
import java.util.List;

public interface MeetingService {
    void createMeeting(CreateMeetingDto meetingDto);
    void updateMeeting(UpdateMeetingDto meetingDto);
    void reactMeeting(String meetingId, String emojiCode);
    void cancelMeeting(String meetingId, boolean isCanceled);
    void deleteMeeting(String meetingId);
    void deleteMeetingsByChannelId(String channelId);
    void deleteMeetingsByTeamId(String teamId);
    List<ResMeetingDto> getVideoChannelMeetings(String channelId, Integer receivedMeetingNum);
}
