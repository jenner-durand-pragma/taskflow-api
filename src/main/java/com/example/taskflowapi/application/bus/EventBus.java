package com.example.taskflowapi.application.bus;

public interface EventBus {
    void publish(Object event);
}
