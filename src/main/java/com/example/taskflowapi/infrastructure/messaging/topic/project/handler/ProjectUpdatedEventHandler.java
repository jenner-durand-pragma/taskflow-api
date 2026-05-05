package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.application.event.integration.project.ProjectUpdatedIntegrationEvent;
import com.example.taskflowapi.application.mediator.Mediator;
import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommand;
import com.example.taskflowapi.application.usecase.updateproject.UpdateProjectCommand;
import com.example.taskflowapi.domain.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class ProjectUpdatedEventHandler implements ProjectEventHandler<ProjectUpdatedIntegrationEvent> {
    private final Mediator mediator;

    @Override
    public boolean supports(String eventType) {
        return ProjectUpdatedIntegrationEvent.EVENT_TYPE.equals(eventType);
    }

    @Override
    public void handle(ProjectUpdatedIntegrationEvent event) {
        log.info("Se recibio un proyecto con ID: {} con nombre: {}, descripción {} y estado {}", event.code(), event.name(), event.description(), event.status());

        var command = new UpdateProjectCommand(event.code(), event.name(), event.description(), ProjectStatus.valueOf(event.status()));

        mediator.send(command);
    }

    @Override
    public Class<ProjectUpdatedIntegrationEvent> getEventClass() {
        return ProjectUpdatedIntegrationEvent.class;
    }
}
