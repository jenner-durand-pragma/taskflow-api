package com.example.taskflowapi.application.event.integration.project;

public record ProjectCreatedIntegrationEvent(
        String code,
        String name,
        String description
) implements ProjectEvent {
    public static String EVENT_TYPE = "PROJECT_CREATED";
}
