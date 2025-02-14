package meetingteam.meetingservice.dtos.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResUserDto {
    private String id;

    private String email;

    private String nickName;

    private String urlIcon;

    private Boolean gender;

    private LocalDate birthday;

    private String phoneNumber;

    private LocalDateTime lastActive;

    private Boolean isOnline;
}
