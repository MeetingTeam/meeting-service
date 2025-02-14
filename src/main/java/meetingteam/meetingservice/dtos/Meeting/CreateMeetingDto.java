package meetingteam.meetingservice.dtos.Meeting;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class CreateMeetingDto {
    @NotBlank
    private String title;

    @NotBlank
    private String teamId;

    @NotBlank
    private String channelId;

    private LocalTime scheduledTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private Set<Integer> scheduledDaysOfWeek;
}
