package meetingteam.meetingservice.dtos.Meeting;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
public class UpdateMeetingDto {
    @NotBlank
    private String id;

    private String title;

    private Boolean isNotify;

    private LocalTime scheduledTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private Set<Integer> scheduledDaysOfWeek;
}
