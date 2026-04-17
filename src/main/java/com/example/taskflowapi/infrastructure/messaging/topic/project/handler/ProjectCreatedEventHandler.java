package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.domain.event.project.ProjectCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectCreatedEventHandler implements ProjectEventHandler<ProjectCreatedEvent> {
    @Override
    public boolean supports(String eventType) {
        return ProjectCreatedEvent.EVENT_TYPE.equals(eventType);
    }

    @Override
    public void handle(Object eventData) {
        var event = (ProjectCreatedEvent) eventData;
        log.info("Se recibio un proyecto con ID: {} con nombre: {} y descripción {}", event.id(), event.name(), event.description());
    }

    @Override
    public Class<ProjectCreatedEvent> getEventClass() {
        return ProjectCreatedEvent.class;
    }
}
