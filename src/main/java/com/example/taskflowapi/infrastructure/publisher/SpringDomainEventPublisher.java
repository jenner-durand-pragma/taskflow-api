package com.example.taskflowapi.infrastructure.publisher;

import com.example.taskflowapi.application.publisher.DomainEventPublisher;
import com.example.taskflowapi.domain.model.Model;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Model model) {
        model.getDomainEvents().forEach(publisher::publishEvent);
    }

    @Override
    public void publish(Model ...model) {
        Arrays.stream(model).forEach(this::publish);
    }
}
