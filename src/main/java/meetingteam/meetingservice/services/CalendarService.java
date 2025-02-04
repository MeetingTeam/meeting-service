package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.Calendar.CalendarDto;

public interface CalendarService {
    void addToCalendar(String meetingId, boolean isAdded);
    CalendarDto getMeetingsOfWeek(Integer week);
}
