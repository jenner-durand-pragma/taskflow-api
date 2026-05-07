package com.example.taskflowapi.application.usecase.createproject;

import com.example.taskflowapi.application.publisher.DomainEventPublisher;
import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommand;
import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommandHandler;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Model;
import com.example.taskflowapi.domain.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProjectCommandHandlerTest {
    @Mock
    private ProjectRepository repository;

    @Mock
    private DomainEventPublisher publisher;

    @InjectMocks
    private CreateProjectCommandHandler handler;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .code("PRJ-1234")
                .name("Proyecto")
                .description("Ejemplo")
                .build();
    }

    @Test
    void handleShouldCallRepositoryAndReturnSavedProject() {
        var command = new CreateProjectCommand(project.getCode(), project.getName(), project.getDescription());
        when(repository.create(any(Project.class))).thenReturn(project);

        var result = handler.handle(command);

        assertNotNull(result);
        assertEquals(project.getCode(), result.getCode(), "El codigo del proyecto esperado por guardarse no coincide con el persistido");

        verify(repository, times(1)).create(any(Project.class));
        verify(publisher, times(1)).publish(project);
    }

    @Test
    void handleShouldThrowIllegalArgumentExceptionWhenCodeAlreadyExists() {
        var command = new CreateProjectCommand(project.getCode(), project.getName(), project.getDescription());

        when(repository.existsByCode(project.getCode())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> handler.handle(command));

        verify(repository, never()).create(any(Project.class));
    }

    @Test
    void handleShouldGenerateCodeWhenCommandCodeIsNull() {
        var commandWithoutCode = new CreateProjectCommand(null, "Name", "Desc");

        when(repository.existsByCode(anyString())).thenReturn(false);
        when(repository.create(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = handler.handle(commandWithoutCode);

        assertNotNull(result.getCode(), "El handler debió haber asignado un código generado");
        assertFalse(result.getCode().isBlank());

        verify(repository, times(1)).existsByCode(anyString());
        verify(repository, times(1)).create(any(Project.class));
    }

    @Test
    void handleShouldGenerateCodeAfterSeveralAttempts() {
        var commandWithoutCode = new CreateProjectCommand("", "Name", "Desc");

        when(repository.existsByCode(anyString()))
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(repository.create(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = handler.handle(commandWithoutCode);

        assertNotNull(result.getCode());
        verify(repository, times(3)).existsByCode(anyString());
        verify(repository, times(1)).create(any(Project.class));
    }
}
