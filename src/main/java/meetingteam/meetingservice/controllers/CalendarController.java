package meetingteam.meetingservice.controllers;

import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.dtos.Calendar.CalendarDto;
import meetingteam.meetingservice.services.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/{meetingId}")
    public ResponseEntity<Void> addToCalendar(
            @PathVariable("meetingId") String meetingId,
            @RequestParam("isAdded") Boolean isAdded){
        calendarService.addToCalendar(meetingId, isAdded);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/week/{week}")
    public ResponseEntity<CalendarDto> getMeetingsOfWeek(
            @PathVariable("week") Integer week){
        return ResponseEntity.ok(calendarService.getMeetingsOfWeek(week));
    }
}
