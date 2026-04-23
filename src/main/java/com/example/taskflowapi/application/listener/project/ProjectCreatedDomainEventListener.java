package com.example.taskflowapi.application.listener.project;

import com.example.taskflowapi.application.bus.EventBus;
import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.application.listener.contract.DomainEventListener;
import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class ProjectCreatedDomainEventListener implements DomainEventListener<ProjectCreatedDomainEvent> {
    private final EventBus bus;

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(ProjectCreatedDomainEvent event) {
        var integrationEvent = new ProjectCreatedIntegrationEvent(event.id(), event.name(), event.description());

        bus.send(integrationEvent, String.valueOf(event.id()), ProjectCreatedIntegrationEvent.EVENT_TYPE);
    }
}
