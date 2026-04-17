package com.example.taskflowapi.presentation.apirest.dto.getprojects;

import com.example.taskflowapi.application.dto.pagination.PageRequest;
import com.example.taskflowapi.application.usecase.getprojects.GetProjectsQuery;
import jakarta.validation.constraints.NotNull;

public record GetProjectsRequest(
        String search,
        @NotNull(message = "Se requiere un numero de pagina")
        Integer page,
        @NotNull(message = "Se requiere la cantidad por pagina")
        Integer size
) {
    public GetProjectsQuery toQuery() {
        return new GetProjectsQuery(
                search,
                PageRequest.of(page, size)
        );
    }
}
