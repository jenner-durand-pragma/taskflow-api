package com.example.taskflowapi.application.usecase.updateproject;

import com.example.taskflowapi.application.mediator.Command;
import com.example.taskflowapi.domain.enums.ProjectStatus;
import com.example.taskflowapi.domain.model.Project;

public record UpdateProjectCommand(
        String code,
        String name,
        String description,
        ProjectStatus status
) implements Command<Project> {
}
