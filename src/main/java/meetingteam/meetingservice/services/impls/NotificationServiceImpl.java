package meetingteam.meetingservice.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetingteam.commonlibrary.exceptions.InternalServerException;
import meetingteam.meetingservice.dtos.Notification.MailDto;
import meetingteam.meetingservice.models.Meeting;
import meetingteam.meetingservice.services.NotificationService;
import meetingteam.meetingservice.services.RabbitmqService;
import meetingteam.meetingservice.services.TeamService;
import meetingteam.meetingservice.services.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final RabbitmqService rabbitmqService;
    private final TeamService teamService;
    private final UserService userService;

    @Override
    public void notifyNewMeeting(Meeting meeting){
        var memberUserIds = teamService.getMemberUserIdsOfTeam(meeting.getTeamId());
                
        var content = String.format(
            "📅 A new meeting \"%s\" has scheduled on %s.",
            meeting.getTitle(),
            meeting.getCreatedAt()
        );
        var mailDto = new MailDto(
        "New Meeting Scheduled",
            content,
            memberUserIds);
        rabbitmqService.sendEmailNotification(mailDto);
    }

    @Override
    public void notifyCanceledMeeting(Meeting meeting){
        var memberUserIds = teamService.getMemberUserIdsOfTeam(meeting.getTeamId());
                
        var content = String.format(
        "📅 The meeting \"%s\" has been %s on %s.",
            meeting.getTitle(),
            meeting.getIsCanceled() ? "canceled" : "reopened",
            LocalDateTime.now()
        );
        var mailDto = new MailDto(
        "Meeting Canceled",
            content,
            memberUserIds
        );
        rabbitmqService.sendEmailNotification(mailDto);
    }
}
