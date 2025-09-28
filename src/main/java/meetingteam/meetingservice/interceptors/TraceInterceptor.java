package meetingteam.meetingservice.interceptors;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import meetingteam.meetingservice.configs.AnomalyConfig;
import meetingteam.meetingservice.constraints.AnomalyTypes;
import meetingteam.meetingservice.utils.AnomalyUtil;
import io.opentelemetry.api.trace.Span;

@Component
@RequiredArgsConstructor
public class TraceInterceptor implements HandlerInterceptor {
    private final AnomalyConfig anomalyConfig;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (anomalyConfig.enableStressNg()) {
            AnomalyUtil.markAnomalySpan(AnomalyTypes.STRESS_NG);
        }
        if (anomalyConfig.enableTc()){
            AnomalyUtil.markAnomalySpan(AnomalyTypes.TRAFFIC_CONTROL);
        }
        
        return true;
    }
}
