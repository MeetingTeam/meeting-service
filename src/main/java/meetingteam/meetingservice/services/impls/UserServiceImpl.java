package meetingteam.meetingservice.services.impls;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.meetingservice.configs.ServiceUrlConfig;
import meetingteam.meetingservice.dtos.User.ResUserDto;
import meetingteam.meetingservice.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ServiceUrlConfig serviceUrlConfig;
    private final RestClient restClient;

    @Override
    @Retry(name="restApi")
    @CircuitBreaker(name="restCircuitBreaker")
    public String getUserEmail() {
        String jwtToken= AuthUtil.getJwtToken();

        URI uri= UriComponentsBuilder.fromHttpUrl(serviceUrlConfig.userServiceUrl())
                .path("/user/private/email")
                .build().toUri();

        return restClient.get()
                .uri(uri)
                .headers(h->h.setBearerAuth(jwtToken))
                .retrieve()
                .body(String.class);
    }

    @Override
    public ResUserDto getUserInfo() {
        String jwtToken= AuthUtil.getJwtToken();

        URI uri= UriComponentsBuilder.fromHttpUrl(serviceUrlConfig.userServiceUrl())
                .path("/user")
                .build().toUri();
        
        return restClient.get()
                .uri(uri)
                .headers(h->h.setBearerAuth(jwtToken))
                .retrieve()
                .body(ResUserDto.class);      
    }
}
