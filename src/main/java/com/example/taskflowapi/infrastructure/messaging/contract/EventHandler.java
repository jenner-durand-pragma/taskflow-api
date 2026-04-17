package com.example.taskflowapi.infrastructure.messaging.contract;

public interface EventHandler<T> {
    boolean supports(String eventType);
    void handle(Object eventData);
    Class<T> getEventClass();
}
