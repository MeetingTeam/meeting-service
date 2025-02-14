package meetingteam.meetingservice.services;

import meetingteam.meetingservice.dtos.User.ResUserDto;

public interface UserService {
    String getUserEmail();
    ResUserDto getUserInfo();
}
