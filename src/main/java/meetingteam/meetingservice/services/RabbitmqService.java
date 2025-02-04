package meetingteam.meetingservice.services;

public interface RabbitmqService {
    void sendToTeam(String teamId, String topic, Object payload);
}
