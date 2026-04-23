package com.example.taskflowapi.domain.event.project;

import com.example.taskflowapi.domain.event.DomainEvent;

public record ProjectCreatedDomainEvent(
        Long id,
        String name,
        String description) implements DomainEvent<Long> {
}
