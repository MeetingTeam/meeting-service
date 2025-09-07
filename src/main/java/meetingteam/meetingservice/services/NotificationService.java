package meetingteam.meetingservice.services;

import meetingteam.meetingservice.models.Meeting;

public interface NotificationService {
    void notifyNewMeeting(Meeting meeting);
    void notifyCanceledMeeting(Meeting meeting);
}
