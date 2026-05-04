package com.example.taskflowapi.application.listener.project;

import com.example.taskflowapi.application.listener.contract.DomainEventListener;
import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ProjectCreatedDomainEventListener implements DomainEventListener<ProjectCreatedDomainEvent> {

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(ProjectCreatedDomainEvent event) {
        log.info("Nuevo proyecto creado {}", event.code());
    }
}
