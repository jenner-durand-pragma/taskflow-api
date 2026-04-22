package com.example.taskflowapi.infrastructure.messaging.bus;

import com.example.taskflowapi.application.bus.EventBus;
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

    @Async
    @Override
    public void send(Record event, String key) {
        send(event, key, null);
    }

    @Async
    @Override
    public void send(Record event, String key, String type) {
        var eventMessage = new EventMessage(key, type, event);

        routers.stream()
                .filter(r -> r.supports(event))
                .findFirst()
                .ifPresent(router -> {
                    kafkaTemplate.send(router.topic(), eventMessage.key(), eventMessage);
                });
    }
}
