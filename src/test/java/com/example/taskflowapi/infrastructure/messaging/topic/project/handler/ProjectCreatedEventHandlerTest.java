package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import com.example.taskflowapi.application.mediator.Mediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectCreatedEventHandlerTest {

    @Mock
    private Mediator mediator;

    @InjectMocks
    private ProjectCreatedEventHandler handler;

    @Test
    void supportsShouldReturnTrueWhenEventTypeMatches() {
        var result = handler.supports(ProjectCreatedIntegrationEvent.EVENT_TYPE);

        assertTrue(result, "El handler debe soportar el tipo de evento ProjectCreatedIntegrationEvent.EVENT_TYPE");
    }

    @Test
    void supportsShouldReturnFalseWhenEventTypeDoesNotMatch() {
        var result = handler.supports("OTRO_TIPO_DE_EVENTO_CUALQUIERA");

        assertFalse(result, "El handler NO debe soportar tipos de eventos no reconocidos");
    }

    @Test
    void getEventClassShouldReturnProjectCreatedIntegrationEventClass() {
        var eventClass = handler.getEventClass();

        assertEquals(ProjectCreatedIntegrationEvent.class, eventClass,
                "El handler debe devolver explícitamente la clase ProjectCreatedIntegrationEvent");
    }

    @Test
    void handleShouldExecuteSuccessfullyWhenProvidedWithCorrectEventType() {
        var validEvent = new ProjectCreatedIntegrationEvent("EXAMPLE_CODE", "Test Name", "Test Desc");
        assertDoesNotThrow(() -> handler.handle(validEvent),
                "El método handle no debería lanzar ninguna excepción al recibir el evento correcto");
    }
}