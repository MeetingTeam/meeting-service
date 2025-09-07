package meetingteam.meetingservice.dtos.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MailDto {
    private String subject;

    private String content;

    private List<String> recipientIds;
}
