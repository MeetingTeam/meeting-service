package meetingteam.meetingservice.services.impls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetingteam.commonlibrary.dtos.SocketDto;
import meetingteam.commonlibrary.exceptions.InternalServerException;
import meetingteam.meetingservice.configs.AnomalyConfig;
import meetingteam.meetingservice.constraints.AnomalyTypes;
import meetingteam.meetingservice.dtos.Notification.MailDto;
import meetingteam.meetingservice.services.RabbitmqService;
import meetingteam.meetingservice.utils.AnomalyUtil;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitmqServiceImpl implements RabbitmqService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper=new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final AnomalyConfig anomalyConfig;

    @Value("${rabbitmq.exchange-name}")
    private String exchangeName;
    @Value("${rabbitmq.notification-routing-key}")
    private String notificationRoutingKey;

    public void sendToTeam(String teamId, String topic, Object payload){
        try{
            if(anomalyConfig.enableMissSpan()){
                AnomalyUtil.markAnomalySpan(AnomalyTypes.MISS_SPAN);
            }
            else{
                int loopNum = 1;
                if(anomalyConfig.enableFanoutCall()){
                    loopNum = 10;
                    AnomalyUtil.markAnomalySpan(AnomalyTypes.FAN_OUT_CALL);
                }
                for(int i=0; i<loopNum; i++){
                    String dest= "/topic/team."+teamId;
                    SocketDto socketDto = new SocketDto(dest,topic, payload);
                    String jsonData = objectMapper.writeValueAsString(socketDto);
                    rabbitTemplate.convertAndSend(exchangeName, dest, jsonData);
                }
            }
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }

    public void sendEmailNotification(MailDto mailDto){
        try{
            if(anomalyConfig.enableMissSpan()){
                AnomalyUtil.markAnomalySpan(AnomalyTypes.MISS_SPAN);
            }
            else{
                int loopNum = 1;
                if(anomalyConfig.enableFanoutCall()){
                    loopNum = 10;
                    AnomalyUtil.markAnomalySpan(AnomalyTypes.FAN_OUT_CALL);
                }
                for(int i=0; i<loopNum; i++){
                    String jsonData = objectMapper.writeValueAsString(mailDto);
                    rabbitTemplate.convertAndSend(exchangeName, notificationRoutingKey, jsonData);
                }
            }
        }
        catch(Exception e){
           log.error(e.getMessage());
        }
    }
}
