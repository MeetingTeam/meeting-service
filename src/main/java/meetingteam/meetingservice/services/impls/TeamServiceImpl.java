package meetingteam.meetingservice.services.impls;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.configs.ServiceUrlConfig;
import meetingteam.meetingservice.services.TeamService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final ServiceUrlConfig serviceUrlConfig;
    private final RestClient restClient;

    @Override
    @Retry(name="restApi")
    @CircuitBreaker(name="restCircuitBreaker")
    public boolean isMemberOfTeam(String userId, String teamId, String channelId) {
        var uriBuilder= UriComponentsBuilder.fromHttpUrl(serviceUrlConfig.teamServiceUrl())
                .path("/team-member/private/is-member-of-team")
                .queryParam("userId", userId);
        if(teamId!=null) uriBuilder.queryParam("teamId", teamId);
        if(channelId!=null) uriBuilder.queryParam("channelId", channelId);
        URI uri = uriBuilder.build().toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(Boolean.class);
    }

    @Override
    @Retry(name="restApi")
    @CircuitBreaker(name="restCircuitBreaker")
    public List<String> getMemberUserIdsOfTeam(String teamId) {
        var uriBuilder= UriComponentsBuilder
                .fromHttpUrl(serviceUrlConfig.teamServiceUrl())
                .path("/team-member/private/"+teamId+"/member-user-ids");
        URI uri = uriBuilder.build().toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
