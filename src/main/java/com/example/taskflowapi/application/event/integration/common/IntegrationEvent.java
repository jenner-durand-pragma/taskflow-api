package com.example.taskflowapi.application.event.integration.common;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record IntegrationEvent(
        String eventId,
        Instant occurredOn,
        String eventType,
        Record payload
) {
    public IntegrationEvent(Record payload, String eventType, String eventId) {
        this(
                checkValidEventId(eventId) ? UUID.randomUUID().toString() : eventId,
                Instant.now(),
                eventType,
                payload
        );
    }

    private static Boolean checkValidEventId(String eventId) {
        return Objects.isNull(eventId) || eventId.isBlank();
    }

    public static IntegrationEvent of(Record payload) {
        return new IntegrationEvent(payload, null, null);
    }

    public static IntegrationEvent of(Record payload, String eventType) {
        return new IntegrationEvent(payload, eventType, null);
    }

    public static IntegrationEvent of(Record payload, String eventType, String eventId) {
        return new IntegrationEvent(payload, eventType, eventId);
    }
}