package com.example.taskflowapi.domain.event;

public interface DomainEvent<T> {
    T id();
}
