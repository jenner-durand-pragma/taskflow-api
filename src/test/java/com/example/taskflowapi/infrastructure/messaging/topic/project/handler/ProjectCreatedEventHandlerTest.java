package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectCreatedIntegrationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectCreatedEventHandlerTest {

    private ProjectCreatedEventHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ProjectCreatedEventHandler();
    }

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
        var validEvent = new ProjectCreatedIntegrationEvent(1L, "Test Name", "Test Desc");

        assertDoesNotThrow(() -> handler.handle(validEvent),
                "El método handle no debería lanzar ninguna excepción al recibir el evento correcto");
    }
}