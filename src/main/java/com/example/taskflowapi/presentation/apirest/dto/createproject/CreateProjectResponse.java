package com.example.taskflowapi.presentation.apirest.dto.createproject;

public record CreateProjectResponse(
        Long id,
        String name,
        String description
) { }
