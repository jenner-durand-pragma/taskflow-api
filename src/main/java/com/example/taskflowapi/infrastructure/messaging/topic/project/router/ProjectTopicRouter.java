package com.example.taskflowapi.infrastructure.messaging.topic.project.router;

import com.example.taskflowapi.application.event.integration.project.ProjectEvent;
import com.example.taskflowapi.infrastructure.messaging.contract.TopicRouter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectTopicRouter implements TopicRouter {
    private final String topicName;

    @Override
    public boolean supports(Object event) {
        return event instanceof ProjectEvent;
    }

    @Override
    public String topic() {
        return topicName;
    }
}
