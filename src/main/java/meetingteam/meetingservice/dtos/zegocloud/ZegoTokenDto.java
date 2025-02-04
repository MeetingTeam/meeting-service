package meetingteam.meetingservice.dtos.zegocloud;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZegoTokenDto {
    private Long appId;

    private String token;
}
