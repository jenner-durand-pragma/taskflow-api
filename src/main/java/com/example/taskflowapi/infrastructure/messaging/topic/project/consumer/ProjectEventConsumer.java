package com.example.taskflowapi.infrastructure.messaging.topic.project.consumer;

import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import com.example.taskflowapi.infrastructure.messaging.topic.project.handler.ProjectEventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProjectEventConsumer {
    private final List<ProjectEventHandler<?>> handlers;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topics.project}", groupId = "notification-group")
    public void consume(EventMessage event) {
        handlers.stream()
                .filter(handler -> handler.supports(event.type()))
                .findFirst()
                .ifPresentOrElse(
                        handler -> invokeHandler(handler, event.data()),
                        () -> log.warn("No handler found for {}", event.type())
                );
    }

    private <T> void invokeHandler(ProjectEventHandler<T> handler, Object eventData) {
        try {
            T typedEvent = mapper.convertValue(eventData, handler.getEventClass());

            handler.handle(typedEvent);
        } catch (IllegalArgumentException e) {
            log.error("Error al castear la data al evento esperado: {}", handler.getEventClass().getSimpleName(), e);
        }
    }
}
