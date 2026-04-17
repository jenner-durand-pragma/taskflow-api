package com.example.taskflowapi.application.usecase.getprojects;

import com.example.taskflowapi.application.dto.pagination.PageRequest;
import com.example.taskflowapi.application.dto.pagination.PaginatedResult;
import com.example.taskflowapi.application.mediator.Query;
import com.example.taskflowapi.domain.model.Project;

public record GetProjectsQuery(
        String search,
        PageRequest pageRequest
) implements Query<PaginatedResult<Project>> {
}
