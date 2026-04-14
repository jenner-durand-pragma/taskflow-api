package com.example.taskflowapi.core.application.getprojects;

import com.example.taskflowapi.shared.common.dto.pagination.PageRequest;
import com.example.taskflowapi.shared.common.dto.pagination.PaginatedResult;
import com.example.taskflowapi.shared.mediator.Query;
import com.example.taskflowapi.core.domain.model.Project;

public record GetProjectsQuery(
        String search,
        PageRequest pageRequest
) implements Query<PaginatedResult<Project>> {
}
