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

        ensureValidCode(project, command.code());

        var savedProject = repository.create(project);
        publisher.publish(savedProject);

        return savedProject;
    }

    private void ensureValidCode(Project project, String originalCode) {
        if (originalCode == null || originalCode.isBlank()) {
            assignUniqueCode(project);
            return;
        }

        if (repository.existsByCode(project.getCode())) {
            throw new IllegalArgumentException(
                    String.format("El proyecto con código '%s' ya existe en el sistema.", project.getCode())
            );
        }
    }

    private void assignUniqueCode(Project project) {
        var finalCode = Stream.iterate(0, i -> i + 1)
                .limit(10)
                .map(project::getCodeWithSuffix)
                .filter(code -> !repository.existsByCode(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Límite de intentos superado."));

        project.setCode(finalCode);
    }
}
