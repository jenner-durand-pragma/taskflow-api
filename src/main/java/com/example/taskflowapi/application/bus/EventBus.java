package com.example.taskflowapi.application.bus;

import com.example.taskflowapi.application.event.integration.common.IntegrationEvent;

public interface EventBus {
    void send(IntegrationEvent event);
}
