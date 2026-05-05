package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.application.event.integration.project.ProjectUpdatedIntegrationEvent;
import com.example.taskflowapi.application.mediator.Mediator;
import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommand;
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
        return false;
    }

    @Override
    public void handle(ProjectUpdatedIntegrationEvent event) {

    }

    @Override
    public Class<ProjectUpdatedIntegrationEvent> getEventClass() {
        return null;
    }
}
