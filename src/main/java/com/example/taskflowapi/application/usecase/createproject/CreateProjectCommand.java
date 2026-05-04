package com.example.taskflowapi.application.usecase.createproject;

import com.example.taskflowapi.application.mediator.Command;
import com.example.taskflowapi.domain.model.Project;

public record CreateProjectCommand(
        String code,
        String name,
        String description
) implements Command<Project> {
    public Project toProject() {
        return Project.create(name, description);
    }
}
