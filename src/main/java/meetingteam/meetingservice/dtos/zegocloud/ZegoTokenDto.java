package meetingteam.meetingservice.dtos.zegocloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import meetingteam.meetingservice.dtos.User.ResUserDto;

@Data
@AllArgsConstructor
public class ZegoTokenDto {
    private Long appId;

    private String token;

    private ResUserDto user;
}
