package meetingteam.meetingservice.services.impls;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.constraints.WebsocketTopics;
import meetingteam.meetingservice.dtos.Meeting.DeleteMeetingDto;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;
import meetingteam.meetingservice.services.RabbitmqService;
import meetingteam.meetingservice.services.WebsocketService;

@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService{
          private final RabbitmqService rabbitmqService;

          @Override
          public void addOrUpdateMeeting(String destTeamId, ResMeetingDto meetingDto) {
                    rabbitmqService.sendToTeam(destTeamId, WebsocketTopics.AddOrUpdateMeeting, meetingDto);
          }

          @Override
          public void deleteMeeting(String destTeamId, DeleteMeetingDto delMeetingDto) {
                    rabbitmqService.sendToTeam(destTeamId,WebsocketTopics.DeleteMeeting, delMeetingDto);
          }
}
