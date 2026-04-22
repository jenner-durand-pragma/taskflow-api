package com.example.taskflowapi.infrastructure.messaging.contract;

public interface TopicRouter {
    boolean supports(Object event);
    String topic();
}
