package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.Notification.MailDto;

public interface RabbitmqService {
    void sendToTeam(String teamId, String topic, Object payload);
    void sendEmailNotification(MailDto mailDto);
}
