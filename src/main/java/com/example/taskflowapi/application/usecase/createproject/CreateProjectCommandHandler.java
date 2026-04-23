package com.example.taskflowapi.application.usecase.createproject;

import com.example.taskflowapi.application.mediator.CommandHandler;
import com.example.taskflowapi.application.publisher.DomainEventPublisher;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProjectCommandHandler implements CommandHandler<CreateProjectCommand, Project> {
    private final ProjectRepository repository;
    private final DomainEventPublisher publisher;

    @Override
    public Project handle(CreateProjectCommand command) {
        var project = repository.create(command.toProject());
        project.raiseCreatedEvent();

        publisher.publish(project);

        return project;
    }
}
