package meetingteam.meetingservice.dtos.Meeting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteMeetingDto {
          private String channelId;

          private String MeetingId;
}
