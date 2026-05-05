package com.example.taskflowapi.application.usecase.updateproject;

import com.example.taskflowapi.application.exception.project.ProjectNotFoundException;
import com.example.taskflowapi.domain.enums.ProjectStatus;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProjectCommandHandlerTest {
    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private UpdateProjectCommandHandler handler;

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
    void handleShouldReturnProject() {
        var newName = "Project Test 2";
        var newDescription = "Example 2";
        var newStatus = ProjectStatus.ACTIVE;
        var command = new UpdateProjectCommand(
                project.getCode(),
                newName,
                newDescription,
                newStatus
        );
        var projectUpdated = Project.builder()
                        .code(project.getCode())
                        .name(newName)
                        .description(newDescription)
                        .status(newStatus)
                        .build();
        when(repository.findByCode(project.getCode())).thenReturn(Optional.of(project));
        when(repository.update(any(Project.class))).thenReturn(projectUpdated);

        var result = handler.handle(command);

        assertNotNull(result);
        assertEquals(project.getCode(), result.getCode(), "El codigo del proyecto esperado por actualizarse no coincide con el persistido");

        verify(repository, times(1)).findByCode(project.getCode());
        verify(repository, times(1)).update(any(Project.class));
    }

    @Test
    void handleShouldThrowExceptionIfNotExists() {
        var newName = "Project Test 2";
        var newDescription = "Example 2";
        var newStatus = ProjectStatus.ACTIVE;
        var command = new UpdateProjectCommand(
                project.getCode(),
                newName,
                newDescription,
                newStatus
        );
        when(repository.findByCode(any(String.class))).thenReturn(Optional.empty());

        var exception = assertThrows(
                ProjectNotFoundException.class,
                () -> handler.handle(command)
        );

        assertEquals("ERR-PRJ-001", exception.getErrorCode());
        assertEquals("Project", exception.getResource());
        assertEquals(command.code(), exception.getResourceId());

        verify(repository, times(1)).findByCode(any(String.class));
    }
}
