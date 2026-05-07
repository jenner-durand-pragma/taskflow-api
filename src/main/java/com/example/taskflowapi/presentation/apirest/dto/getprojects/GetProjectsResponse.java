package com.example.taskflowapi.presentation.apirest.dto.getprojects;

public record GetProjectsResponse(
        String code,
        String name,
        String description,
        String status
) {
}
