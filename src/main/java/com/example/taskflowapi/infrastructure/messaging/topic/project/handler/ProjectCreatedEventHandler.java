package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectCreatedEventHandler implements ProjectEventHandler<ProjectCreatedIntegrationEvent> {
    @Override
    public boolean supports(String eventType) {
        return ProjectCreatedIntegrationEvent.EVENT_TYPE.equals(eventType);
    }

    @Override
    public void handle(Object eventData) {
        var event = (ProjectCreatedIntegrationEvent) eventData;
        log.info("Se recibio un proyecto con ID: {} con nombre: {} y descripción {}", event.id(), event.name(), event.description());
    }

    @Override
    public Class<ProjectCreatedIntegrationEvent> getEventClass() {
        return ProjectCreatedIntegrationEvent.class;
    }
}
