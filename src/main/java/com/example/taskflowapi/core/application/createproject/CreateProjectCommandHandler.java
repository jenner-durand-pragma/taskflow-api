package com.example.taskflowapi.core.application.createproject;

import com.example.taskflowapi.shared.mediator.CommandHandler;
import com.example.taskflowapi.core.domain.gateway.ProjectRepository;
import com.example.taskflowapi.core.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProjectCommandHandler implements CommandHandler<CreateProjectCommand, Project> {
    private final ProjectRepository repository;

    @Override
    public Project handle(CreateProjectCommand command) {
        return repository.create(command.toProject());
    }
}
