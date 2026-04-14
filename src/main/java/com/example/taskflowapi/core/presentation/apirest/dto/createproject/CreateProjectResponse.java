package com.example.taskflowapi.core.presentation.apirest.dto.createproject;

public record CreateProjectResponse(
        Long id,
        String name,
        String description
) { }
