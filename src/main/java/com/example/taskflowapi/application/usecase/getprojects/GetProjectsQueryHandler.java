package com.example.taskflowapi.application.usecase.getprojects;

import com.example.taskflowapi.application.dto.pagination.PaginatedResult;
import com.example.taskflowapi.application.mediator.QueryHandler;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProjectsQueryHandler implements QueryHandler<GetProjectsQuery, PaginatedResult<Project>> {
    private final ProjectRepository repository;

    @Override
    public PaginatedResult<Project> handle(GetProjectsQuery query) {
        var page = query.pageRequest().page();
        var size = query.pageRequest().size();
        var search = query.search();

        var projects = repository.searchPaginated(search, page, size);
        var totalElements = repository.count(search);

        return PaginatedResult.of(
                projects,
                totalElements,
                page,
                size
        );
    }
}
