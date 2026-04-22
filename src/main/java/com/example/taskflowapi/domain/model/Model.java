package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.event.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Model<T> {
    private final List<DomainEvent<T>> domainEvents = new ArrayList<>();

    protected void raise(DomainEvent<T> domainEvent) {
        domainEvents.add(domainEvent);
    }
}
