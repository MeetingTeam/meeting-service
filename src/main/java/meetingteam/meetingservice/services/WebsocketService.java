package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.Meeting.DeleteMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;

public interface WebsocketService {
          void addOrUpdateMeeting(String destTeamId, ResMeetingDto meetingDto);
          void deleteMeeting(String destTeamId, DeleteMeetingDto delMeetingDto);
}
