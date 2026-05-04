package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.event.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Model {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void raise(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }
}
