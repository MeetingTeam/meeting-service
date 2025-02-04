package meetingteam.meetingservice.services.impls;

import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.exceptions.BadRequestException;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.meetingservice.dtos.zegocloud.ZegoTokenDto;
import meetingteam.meetingservice.repositories.MeetingRepository;
import meetingteam.meetingservice.services.TeamService;
import meetingteam.meetingservice.services.ZegoService;
import meetingteam.meetingservice.utils.TokenServerAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZegoServiceImpl implements ZegoService {
    private final MeetingRepository meetingRepo;
    private final TeamService teamService;

    @Value("${zegocloud.app-id}")
    private long zegoAppId;

    @Value("${zegocloud.secret-server}")
    private String zegoSecretServer;

    @Override
    public ZegoTokenDto generateToken(String meetingId) {
        String userId= AuthUtil.getUserId();
        var meeting=meetingRepo.findById(meetingId).orElseThrow(()->new BadRequestException("Meeting not found"));
        if(meeting.getIsCanceled()!=null && meeting.getIsCanceled())
            throw new BadRequestException("This meeting has been closed");

        if(!teamService.isMemberOfTeam(userId, meeting.getChannelId()))
            throw new AccessDeniedException("You do not have permission to get token from this meeting");

        String token= generateToken(userId, meetingId);
        return new ZegoTokenDto(zegoAppId, token);
    }

    private String generateToken(String userId, String roomId) {
        String payload = String.format("{\"room_id\":\"%s\"}", roomId);
        TokenServerAssistant.TokenInfo token=
                TokenServerAssistant.generateToken04(zegoAppId,userId,zegoSecretServer,3600*24,payload);
        return token.data;
    }
}
