package com.example.taskflowapi.core.presentation.apirest.dto.getprojects;

public record GetProjectsResponse(
        Long id,
        String name,
        String description
) {
}
