package meetingteam.meetingservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Meeting")
public class Meeting {
    @Id
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
