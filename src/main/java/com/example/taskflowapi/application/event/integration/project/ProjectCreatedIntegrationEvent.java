package com.example.taskflowapi.application.event.integration.project;

public record ProjectCreatedIntegrationEvent(
        Long id,
        String name,
        String description
) implements ProjectEvent {
    public static String EVENT_TYPE = "PROJECT_CREATED";
}
