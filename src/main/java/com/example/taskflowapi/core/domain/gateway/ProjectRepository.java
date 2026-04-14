package com.example.taskflowapi.core.domain.gateway;

import com.example.taskflowapi.core.domain.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project create(Project project);
    Project update(Project project);
    List<Project> searchPaginated(String search, Integer page, Integer size);
    Long count(String search);
}
