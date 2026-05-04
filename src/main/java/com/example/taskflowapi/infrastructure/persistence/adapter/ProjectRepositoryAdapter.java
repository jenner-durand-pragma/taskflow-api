package com.example.taskflowapi.infrastructure.persistence.adapter;

import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.infrastructure.persistence.mapper.ProjectEntityMapper;
import com.example.taskflowapi.infrastructure.persistence.repository.ProjectEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class ProjectRepositoryAdapter implements ProjectRepository {
    private ProjectEntityRepository repository;
    private ProjectEntityMapper mapper;

    @Override
    public Project create(Project project) {
        var entity = mapper.toEntity(project);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public Project update(Project project) {
        var entity = mapper.toEntity(project);

        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public Boolean existsByCode(String code) {
        return repository.existsByCodeIgnoreCase(code);
    }

    @Override
    public List<Project> searchPaginated(String search, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);

        var list = Objects.isNull(search) || search.isBlank()
        ? repository.findAllBy(pageable)
        : repository.findByCodeOrNameOrDescriptionContainingIgnoreCase(search, search, search, pageable);

        return list.stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Long count(String search) {
        return Objects.isNull(search) || search.isBlank()
                ? repository.count()
                : repository.countByCodeOrNameOrDescriptionContainingIgnoreCase(search, search, search);
    }
}
