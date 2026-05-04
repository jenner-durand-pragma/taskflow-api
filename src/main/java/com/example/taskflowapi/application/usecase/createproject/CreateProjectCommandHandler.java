package com.example.taskflowapi.application.usecase.createproject;

import com.example.taskflowapi.application.mediator.CommandHandler;
import com.example.taskflowapi.application.publisher.DomainEventPublisher;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CreateProjectCommandHandler implements CommandHandler<CreateProjectCommand, Project> {
    private final ProjectRepository repository;
    private final DomainEventPublisher publisher;

    @Override
    public Project handle(CreateProjectCommand command) {
        var project = command.toProject();
        var finalCode = Stream.iterate(0, i -> i + 1)
                .limit(10)
                .map(project::getCodeWithSuffix)
                .filter(code -> !repository.existsByCode(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Se superó el límite máximo de 10 intentos para generar un código único."));
        project.setCode(finalCode);
        project = repository.create(command.toProject());

        publisher.publish(project);

        return project;
    }
}
