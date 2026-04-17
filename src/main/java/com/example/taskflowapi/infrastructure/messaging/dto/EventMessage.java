package com.example.taskflowapi.infrastructure.messaging.dto;

public record EventMessage(
        String eventId,
        String eventType,
        Object data
) { }
