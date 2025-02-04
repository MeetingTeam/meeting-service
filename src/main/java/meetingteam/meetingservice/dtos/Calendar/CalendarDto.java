package meetingteam.meetingservice.dtos.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import meetingteam.meetingservice.dtos.Meeting.ResMeetingDto;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CalendarDto {
    private List<ResMeetingDto> meetings;
    private List<LocalDate> weekRange;
}
