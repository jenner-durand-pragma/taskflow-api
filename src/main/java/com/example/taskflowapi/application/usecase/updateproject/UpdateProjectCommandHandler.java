package com.example.taskflowapi.application.usecase.updateproject;

import com.example.taskflowapi.application.exception.project.ProjectNotFoundException;
import com.example.taskflowapi.application.mediator.CommandHandler;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProjectCommandHandler implements CommandHandler<UpdateProjectCommand, Project> {
    private final ProjectRepository projectRepository;

    @Override
    public Project handle(UpdateProjectCommand command) {
        var project = projectRepository.findByCode(command.code())
                .orElseThrow(() -> new ProjectNotFoundException(command.code()));

        project.update(command.name(), command.description(), command.status());

        return projectRepository.update(project);
    }
}
