package com.example.taskflowapi.domain.event;

public interface DomainEvent {
    String getEventId();
    String getEventType();
}
