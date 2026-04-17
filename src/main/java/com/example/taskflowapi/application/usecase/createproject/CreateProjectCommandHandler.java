package com.example.taskflowapi.application.usecase.createproject;

import com.example.taskflowapi.application.bus.EventBus;
import com.example.taskflowapi.application.mediator.CommandHandler;
import com.example.taskflowapi.domain.event.project.ProjectCreatedEvent;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProjectCommandHandler implements CommandHandler<CreateProjectCommand, Project> {
    private final ProjectRepository repository;
    private final EventBus bus;

    @Override
    public Project handle(CreateProjectCommand command) {
        var project = repository.create(command.toProject());
        bus.publish(ProjectCreatedEvent.fromProject(project));

        return project;
    }
}
