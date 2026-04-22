package com.example.taskflowapi.application.bus;

public interface EventBus {
    void send(Record event, String key, String type);
    void send(Record event, String key);
}
