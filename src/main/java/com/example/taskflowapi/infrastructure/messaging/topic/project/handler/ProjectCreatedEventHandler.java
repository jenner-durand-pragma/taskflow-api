package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.application.mediator.Mediator;
import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class ProjectCreatedEventHandler implements ProjectEventHandler<ProjectCreatedIntegrationEvent> {
    private final Mediator mediator;

    @Override
    public boolean supports(String eventType) {
        return ProjectCreatedIntegrationEvent.EVENT_TYPE.equals(eventType);
    }

    @Override
    public void handle(ProjectCreatedIntegrationEvent event) {
        log.info("Se recibio un proyecto con ID: {} con nombre: {} y descripción {}", event.code(), event.name(), event.description());

        var command = new CreateProjectCommand(event.code(), event.name(), event.description());

        mediator.send(command);
    }

    @Override
    public Class<ProjectCreatedIntegrationEvent> getEventClass() {
        return ProjectCreatedIntegrationEvent.class;
    }
}
