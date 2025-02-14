package meetingteam.meetingservice.repositories;

import meetingteam.commonlibrary.repositories.BaseRepository;
import meetingteam.meetingservice.models.Meeting;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface MeetingRepository extends BaseRepository<Meeting, String> {
    List<Meeting> getMeetingsByChannelId(String channelId, Pageable pageable);
    List<Meeting> getByIds(Set<String> meetingIds);
    List<Meeting> getCalendarMeetings(String userId);
    void deleteByChannelId(String channelId);
    void deleteByTeamId(String teamId);
}
