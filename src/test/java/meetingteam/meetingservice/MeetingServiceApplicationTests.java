package meetingteam.meetingservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import meetingteam.meetingservice.dtos.Meeting.CreateMeetingDto;

@SpringBootConfiguration
class MeetingServiceApplicationTests {
    private final Validator validator;

    public MeetingServiceApplicationTests() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void contextLoads() {
        var createMeetingDto= new CreateMeetingDto();
        createMeetingDto.setTitle("Meeting Now");

        Set<ConstraintViolation<CreateMeetingDto>> violations = validator.validate(createMeetingDto);
        assertTrue(!violations.isEmpty(), "DTO should be invalid");
    }
}
