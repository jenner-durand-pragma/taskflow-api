package com.example.taskflowapi.core.application.createproject;

import com.example.taskflowapi.shared.mediator.Command;
import com.example.taskflowapi.core.domain.model.Project;

public record CreateProjectCommand(
        String name,
        String description
) implements Command<Project> {
    public Project toProject() {
        return Project.builder()
                .name(name)
                .description(description)
                .build();
    }
}
