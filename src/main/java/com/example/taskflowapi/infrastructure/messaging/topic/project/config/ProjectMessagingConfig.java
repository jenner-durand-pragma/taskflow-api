package com.example.taskflowapi.infrastructure.messaging.topic.project.config;

import com.example.taskflowapi.infrastructure.messaging.contract.ModuleExternalizationConfigurer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ProjectMessagingConfig {
    @Bean
    public NewTopic projectTopic(@Value("${app.kafka.topics.project}") String projectTopic) {
        return TopicBuilder.name(projectTopic).build();
    }

    @Bean
    public ModuleExternalizationConfigurer projectConfigurer(@Value("${app.kafka.topics.project}") String projectTopic) {
        return new ProjectExternalizationConfigurer(projectTopic);
    }
}
