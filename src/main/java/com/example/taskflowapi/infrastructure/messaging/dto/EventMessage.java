package com.example.taskflowapi.infrastructure.messaging.dto;

public record EventMessage(
        String key,
        String type,
        Object data
) { }
