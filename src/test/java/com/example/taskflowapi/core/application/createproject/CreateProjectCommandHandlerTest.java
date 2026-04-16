package com.example.taskflowapi.core.application.createproject;

import com.example.taskflowapi.core.domain.gateway.ProjectRepository;
import com.example.taskflowapi.core.domain.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateProjectCommandHandlerTest {
    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private CreateProjectCommandHandler handler;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .name("Proyecto")
                .description("Ejemplo")
                .build();
    }

    @Test
    void handleShouldCallRepositoryAndReturnSavedProject() {
        var command = new CreateProjectCommand(project.getName(), project.getDescription());

        when(repository.create(any(Project.class))).thenReturn(project);

        var result = handler.handle(command);

        assertNotNull(result);
        assertEquals(project.getId(), result.getId(), "El ID del proyecto esperado por guardarse no coincide con el persistido");
    }
}
