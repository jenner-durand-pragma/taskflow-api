package com.example.taskflowapi.infrastructure.messaging.topic.project.config;

import com.example.taskflowapi.domain.event.project.ProjectEvent;
import com.example.taskflowapi.infrastructure.messaging.contract.ModuleExternalizationConfigurer;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

@AllArgsConstructor
public class ProjectExternalizationConfigurer implements ModuleExternalizationConfigurer {
    private final String projectTopicName;

    @Override
    public boolean supports(Object event) {
        return event instanceof ProjectEvent;
    }

    @Override
    public EventExternalizationConfiguration.Router configure(EventExternalizationConfiguration.Router builder) {
        return builder.route(
                ProjectEvent.class,
                event -> RoutingTarget.forTarget(projectTopicName).andKey(event.getEventId())
        );
    }
}
