package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.zegocloud.ZegoTokenDto;

public interface ZegoService {
    ZegoTokenDto generateToken(String meetingId);
}
