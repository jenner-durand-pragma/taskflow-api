package com.example.taskflowapi.presentation.apirest.dto.getprojects;

public record GetProjectsResponse(
        Long id,
        String name,
        String description
) {
}
