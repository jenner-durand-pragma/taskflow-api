package com.example.taskflowapi.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .name("Project test")
                .build();
    }

    @Test
    void createProjectShouldCreateSuccessfully() {
        var projectCreated = Project.create(project.getName(), project.getDescription());

        assertEquals(project.getName(), projectCreated.getName(), "Los projects deben tener el mismo nombre");
        assertEquals(project.getDescription(), projectCreated.getDescription(), "Los projects deben tener la misma descripcion");
    }

    @Test
    void raiseCreatedEventShouldPublishedAsDomainEvent() {
        project.raiseCreatedEvent();

        assertEquals(1, project.getDomainEvents().size(), "El project debe contener solo 1 domain event");
    }
}
