package com.example.taskflowapi.application.listener.project;

import com.example.taskflowapi.application.bus.EventBus;
import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjectCreatedDomainEventListenerTest {

    @Mock
    private EventBus bus;

    @InjectMocks
    private ProjectCreatedDomainEventListener listener;

    @Captor
    private ArgumentCaptor<ProjectCreatedIntegrationEvent> eventCaptor;

    @Captor
    private ArgumentCaptor<String> eventIdCaptor;

    @Captor
    private ArgumentCaptor<String> eventTypeCaptor;

    @Test
    void onShouldMapDomainEventToIntegrationEventAndSendToBus() {
        var projectId = 1L;
        var projectName = "Project Test";
        var projectDesc = "Description Test";
        var domainEvent = new ProjectCreatedDomainEvent(projectId, projectName, projectDesc);

        listener.on(domainEvent);

        verify(bus, times(1)).send(eventCaptor.capture(), eventIdCaptor.capture(), eventTypeCaptor.capture());

        ProjectCreatedIntegrationEvent capturedEvent = eventCaptor.getValue();
        var capturedId = eventIdCaptor.getValue();
        var capturedType = eventTypeCaptor.getValue();

        assertEquals(projectId, capturedEvent.id());
        assertEquals(projectName, capturedEvent.name());
        assertEquals(projectDesc, capturedEvent.description());
        assertEquals(String.valueOf(projectId), capturedId);
        assertEquals(ProjectCreatedIntegrationEvent.EVENT_TYPE, capturedType);
    }
}