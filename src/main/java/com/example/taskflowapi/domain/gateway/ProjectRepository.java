package com.example.taskflowapi.domain.gateway;

import com.example.taskflowapi.domain.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project create(Project project);
    Project update(Project project);
    Boolean existsByCode(String code);
    List<Project> searchPaginated(String search, Integer page, Integer size);
    Long count(String search);
}
