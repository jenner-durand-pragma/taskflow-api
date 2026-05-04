package com.example.taskflowapi.infrastructure.messaging.bus;

import com.example.taskflowapi.application.bus.EventBus;
import com.example.taskflowapi.application.event.integration.common.IntegrationEvent;
import com.example.taskflowapi.infrastructure.messaging.contract.TopicRouter;
import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class KafkaEventBus implements EventBus {
    private final List<TopicRouter> routers;
    private final KafkaTemplate<String, EventMessage> kafkaTemplate;

    @Override
    public void send(IntegrationEvent event) {
        var eventMessage = new EventMessage(event.eventId(), event.eventType(), event.payload());

        routers.stream()
                .filter(r -> r.supports(event.payload()))
                .findFirst()
                .ifPresent(router -> {
                    kafkaTemplate.send(router.topic(), eventMessage.key(), eventMessage);
                });
    }
}
