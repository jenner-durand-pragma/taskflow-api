package com.example.taskflowapi.domain.event.project;

import com.example.taskflowapi.domain.model.Project;

public record ProjectCreatedEvent(
        Long id,
        String name,
        String description
) implements ProjectEvent {
    public static String EVENT_TYPE = "PROJECT_CREATED";

    public static ProjectCreatedEvent fromProject(Project project) {
        return new ProjectCreatedEvent(project.getId(), project.getName(), project.getDescription());
    }

    @Override
    public String getEventId() {
        return id.toString();
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
}
