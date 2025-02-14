package meetingteam.meetingservice.dtos.Meeting;

import lombok.Data;
import meetingteam.meetingservice.models.Reaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
public class ResMeetingDto {
    private String id;

    private Boolean isCanceled;

    private String title;

    private String teamId;

    private String channelId;

    private String creatorId;

    private LocalTime scheduledTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private Set<Integer> scheduledDaysOfWeek;

    private Set<String> calendarUserIds;

    private List<Reaction> reactions;

    private LocalDateTime createdAt;
}
