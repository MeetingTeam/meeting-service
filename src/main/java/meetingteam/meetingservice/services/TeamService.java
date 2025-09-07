package meetingteam.meetingservice.services;

import java.util.List;

import meetingteam.meetingservice.models.Meeting;

public interface TeamService {
    boolean isMemberOfTeam(String userId,String teamId, String channelId);
    List<String> getMemberUserIdsOfTeam(String teamId);
}
