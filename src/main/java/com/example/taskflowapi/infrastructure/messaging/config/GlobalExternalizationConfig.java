package com.example.taskflowapi.infrastructure.messaging.config;

import com.example.taskflowapi.domain.event.DomainEvent;
import com.example.taskflowapi.infrastructure.messaging.contract.ModuleExternalizationConfigurer;
import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.EventExternalizationConfiguration;

import java.util.List;

@Configuration
public class GlobalExternalizationConfig {
    @Bean
    public EventExternalizationConfiguration externalizationConfiguration(List<ModuleExternalizationConfigurer> configurers) {
        var builder = EventExternalizationConfiguration.externalizing()
                .select(event -> configurers.stream().anyMatch(c -> c.supports(event)));
        for (ModuleExternalizationConfigurer configurer : configurers) {
            builder = configurer.configure(builder);
        }

        return builder.mapping(DomainEvent.class, o -> new EventMessage(o.getEventId(), o.getEventType(), o)).build();
    }
}
