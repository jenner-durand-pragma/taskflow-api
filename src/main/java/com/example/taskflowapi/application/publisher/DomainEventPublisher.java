package com.example.taskflowapi.application.publisher;

import com.example.taskflowapi.domain.model.Model;

public interface DomainEventPublisher {
    void publish(Model<?> model);
    void publish(Model<?> ...model);
}
