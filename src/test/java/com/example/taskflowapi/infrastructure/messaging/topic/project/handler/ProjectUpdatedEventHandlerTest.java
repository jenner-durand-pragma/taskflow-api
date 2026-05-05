package com.example.taskflowapi.infrastructure.messaging.topic.project.handler;

import com.example.taskflowapi.application.event.integration.project.ProjectUpdatedIntegrationEvent;
import com.example.taskflowapi.application.mediator.Mediator;
import com.example.taskflowapi.application.usecase.updateproject.UpdateProjectCommand;
import com.example.taskflowapi.domain.enums.ProjectStatus;
import com.example.taskflowapi.domain.model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectUpdatedEventHandlerTest {

    @Mock
    private Mediator mediator;

    @InjectMocks
    private ProjectUpdatedEventHandler handler;

    @Test
    void supportsShouldReturnTrueWhenEventTypeMatches() {
        var result = handler.supports(ProjectUpdatedIntegrationEvent.EVENT_TYPE);

        assertTrue(result, "El handler debe soportar el tipo de evento ProjectUpdatedIntegrationEvent.EVENT_TYPE");
    }

    @Test
    void supportsShouldReturnFalseWhenEventTypeDoesNotMatch() {
        var result = handler.supports("OTRO_TIPO_DE_EVENTO_CUALQUIERA");

        assertFalse(result, "El handler NO debe soportar tipos de eventos no reconocidos");
    }

    @Test
    void getEventClassShouldReturnProjectUpdatedIntegrationEventClass() {
        var eventClass = handler.getEventClass();

        assertEquals(ProjectUpdatedIntegrationEvent.class, eventClass,
                "El handler debe devolver explícitamente la clase ProjectUpdatedIntegrationEvent");
    }

    @Test
    void handleShouldExecuteSuccessfullyWhenProvidedWithCorrectEventType() {
        var validEvent = new ProjectUpdatedIntegrationEvent("EXAMPLE_CODE", "Test Name", "Test Desc", "Example status");
        assertDoesNotThrow(() -> handler.handle(validEvent),
                "El método handle no debería lanzar ninguna excepción al recibir el evento correcto");
    }

    @Test
    void handleShouldDispatchCommandWhenStatusIsValid() {
        var validEvent = new ProjectUpdatedIntegrationEvent(
                "PRJ-01",
                "Test",
                "Desc",
                "ACTIVE"
        );

        handler.handle(validEvent);

        var captor = ArgumentCaptor.forClass(UpdateProjectCommand.class);
        verify(mediator, times(1)).send(captor.capture());

        UpdateProjectCommand sentCommand = captor.getValue();
        assertEquals(validEvent.code(), sentCommand.code(), "Los codigos deben ser los mismos");
        assertEquals(validEvent.status(), sentCommand.status().name(), "Los estados deben ser los mismos");
    }

    @Test
    void handleShouldThrowExceptionWhenStatusIsInvalid() {
        var invalidEvent = new ProjectUpdatedIntegrationEvent("PRJ-01", "Test", "Desc", "ESTADO_FALSO");

        assertThrows(
                IllegalArgumentException.class,
                () -> handler.handle(invalidEvent),
                "Debe fallar al intentar parsear un estado que no existe en el Enum"
        );

        verify(mediator, never()).send(any());
    }
}