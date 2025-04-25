package meetingteam.meetingservice;

import meetingteam.meetingservice.configs.ServiceUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServiceUrlConfig.class)
public class MeetingServiceApplication {
    // Comment some thing
    public static void main(String[] args) {
        SpringApplication.run(MeetingServiceApplication.class, args);
    }
}
