package com.example.taskflowapi.application.listener.contract;

public interface DomainEventListener<T> {
    void on(T event);
}
