package com.example.taskflowapi.infrastructure.messaging.topic.project.consumer;

import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import com.example.taskflowapi.infrastructure.messaging.topic.project.handler.ProjectEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@AllArgsConstructor
public class ProjectEventConsumer {
    private final List<ProjectEventHandler<?>> handlers;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topics.project}", groupId = "notification-group")
    public void consume(EventMessage event) {
        var dataNode = mapper.valueToTree(event.data());

        handlers.stream()
                .filter(handler -> handler.supports(event.eventType()))
                .findFirst()
                .ifPresentOrElse(
                        handler -> handler.handle(mapper.treeToValue(dataNode, handler.getEventClass())),
                        () -> System.out.println("No handler found for " + event.eventType())
                );
    }
}
