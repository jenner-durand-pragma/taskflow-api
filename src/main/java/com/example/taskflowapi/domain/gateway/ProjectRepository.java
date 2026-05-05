package com.example.taskflowapi.domain.gateway;

import com.example.taskflowapi.domain.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project create(Project project);
    Project update(Project project);
    Boolean existsByCode(String code);
    Optional<Project> findByCode(String code);
    List<Project> searchPaginated(String search, Integer page, Integer size);
    Long count(String search);
}
