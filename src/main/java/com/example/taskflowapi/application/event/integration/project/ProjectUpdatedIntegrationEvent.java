package com.example.taskflowapi.application.event.integration.project;

public record ProjectUpdatedIntegrationEvent(
    String code,
    String name,
    String description,
    String status
) implements ProjectEvent {
    public static String EVENT_TYPE = "PROJECT_UPDATED";
}
