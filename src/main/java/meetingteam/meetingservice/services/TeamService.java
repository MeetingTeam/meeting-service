package meetingteam.meetingservice.services;

public interface TeamService {
    boolean isMemberOfTeam(String userId, String channelId);
    boolean requestToJoinTeam(String teamId);
}
