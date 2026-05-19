package com.example.taskflowapi.infrastructure.persistence.adapter;

import com.example.taskflowapi.application.exception.common.ReferencedResourceNotFoundException;
import com.example.taskflowapi.domain.gateway.TaskRepository;
import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.domain.model.Task;
import com.example.taskflowapi.infrastructure.persistence.mapper.TaskEntityMapper;
import com.example.taskflowapi.infrastructure.persistence.repository.ProjectEntityRepository;
import com.example.taskflowapi.infrastructure.persistence.repository.TaskEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {
    private final TaskEntityRepository repository;
    private final ProjectEntityRepository projectRepository;
    private final TaskEntityMapper mapper;

    @Override
    public Task create(Task task) {
        var projectEntity = projectRepository.findByCodeIgnoreCase(task.getProjectCode())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(Project.class.getSimpleName(), "code", task.getProjectCode()));
        var taskEntity = mapper.toEntity(task);

        taskEntity.setProject(projectEntity);
        taskEntity = repository.save(taskEntity);

        return mapper.toModel(taskEntity);
    }

    @Override
    public Optional<Task> findByCode(String code) {
        return repository.findByCodeIgnoreCase(code)
                .map(mapper::toModel);
    }
}
