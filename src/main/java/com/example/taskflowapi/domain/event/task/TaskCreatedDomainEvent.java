package com.example.taskflowapi.domain.event.task;

import com.example.taskflowapi.domain.event.DomainEvent;

public record TaskCreatedDomainEvent(
        String code,
        String title,
        String projectCode
) implements DomainEvent {
}
