package com.example.taskflowapi.domain.event.project;

import com.example.taskflowapi.domain.event.DomainEvent;

public record ProjectCreatedDomainEvent(
        String code,
        String name,
        String description) implements DomainEvent {
}
