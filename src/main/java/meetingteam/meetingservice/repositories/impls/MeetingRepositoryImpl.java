package meetingteam.meetingservice.repositories.impls;

import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.models.Meeting;
import meetingteam.meetingservice.repositories.MeetingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Meeting save(Meeting meeting) {
        return mongoTemplate.save(meeting);
    }

    @Override
    public Optional<Meeting> findById(String id) {
        var meeting = mongoTemplate.findById(id, Meeting.class);
        return Optional.ofNullable(meeting);
    }

    @Override
    public void delete(Meeting meeting) {
        mongoTemplate.remove(meeting);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Meeting.class);
    }

    @Override
    public List<Meeting> getMeetingsByChannelId(String channelId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("channelId").is(channelId));
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        return mongoTemplate.find(query, Meeting.class);
    }

    @Override
    public List<Meeting> getByIds(Set<String> meetingIds) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("id").in(meetingIds)
                        .and("scheduledTime").ne(null)
                        .and("isCanceled").is(false)
        );
        return mongoTemplate.find(query, Meeting.class);
    }

    @Override
    public List<Meeting> getCalendarMeetings(String userId) {
        Query query = new Query()
                .addCriteria(Criteria.where("calendarUserIds").in(userId));
        return mongoTemplate.find(query, Meeting.class);
    }

    @Override
    public void deleteByChannelId(String channelId) {
        Query query = new Query()
                .addCriteria(Criteria.where("channelId").is(channelId));
        mongoTemplate.remove(query, Meeting.class);
    }

    @Override
    public void deleteByTeamId(String teamId) {
        Query query = new Query()
                .addCriteria(Criteria.where("teamId").is(teamId));
        mongoTemplate.remove(query, Meeting.class);
    }
}
