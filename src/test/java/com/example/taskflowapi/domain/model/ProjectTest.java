package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.enums.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .code("PRJ-1234")
                .name("Project test")
                .description("Example")
                .build();
    }

    @Test
    void createProjectShouldCreateSuccessfully() {
        var projectCreated = Project.create(project.getName(), project.getDescription());

        assertEquals(project.getName(), projectCreated.getName(), "Los projects deben tener el mismo nombre");
        assertEquals(project.getDescription(), projectCreated.getDescription(), "Los projects deben tener la misma descripcion");
        assertEquals(1, projectCreated.getDomainEvents().size(), "El project debe contener solo 1 domain event");
    }

    @Test
    void updateProjectShouldUpdateSuccessfully() {
        var newName = "Project Test 2";
        var newDescription = "Example 2";
        var newStatus = ProjectStatus.ACTIVE;

        project.update(newName, newDescription, newStatus);

        assertEquals(project.getName(), newName, "Deben tener el mismo nombre");
        assertEquals(project.getDescription(), newDescription, "Deben tener la misma descripcion");
        assertEquals(project.getStatus(), newStatus, "Deben tener el mismo estado");
        assertEquals(1, project.getDomainEvents().size(), "El project debe contener solo 1 domain event");
    }
}
