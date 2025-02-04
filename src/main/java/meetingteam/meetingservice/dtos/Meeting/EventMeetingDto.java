package meetingteam.meetingservice.dtos.Meeting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class EventMeetingDto {
    @NotBlank
    private String id;

    @NotNull
    private LocalTime scheduledTime;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private Set<Integer> scheduledDaysOfWeek;
}
