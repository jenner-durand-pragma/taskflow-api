package com.example.taskflowapi.infrastructure.messaging.contract;

import org.springframework.modulith.events.EventExternalizationConfiguration;

public interface ModuleExternalizationConfigurer {
    boolean supports(Object event);
    EventExternalizationConfiguration.Router configure(EventExternalizationConfiguration.Router builder);
}
