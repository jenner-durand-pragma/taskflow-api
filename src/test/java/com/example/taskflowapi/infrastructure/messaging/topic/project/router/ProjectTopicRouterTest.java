package com.example.taskflowapi.infrastructure.messaging.topic.project.router;

import com.example.taskflowapi.application.event.integration.project.ProjectEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTopicRouterTest {

    private ProjectTopicRouter router;
    private final String EXPECTED_TOPIC = "project-events-topic";

    @BeforeEach
    void setUp() {
        router = new ProjectTopicRouter(EXPECTED_TOPIC);
    }

    @Test
    void supportsShouldReturnTrueWhenEventIsProjectEvent() {
        var validEvent = new ProjectEvent() {};

        assertTrue(router.supports(validEvent), "El router debería soportar eventos de tipo ProjectEvent");
    }

    @Test
    void supportsShouldReturnFalseWhenEventIsNotProjectEvent() {
        record OtherEvent(String name) {}
        var invalidEvent = new OtherEvent("test");

        assertFalse(router.supports(invalidEvent), "El router NO debería soportar eventos ajenos a ProjectEvent");
    }

    @Test
    void topicShouldReturnConfiguredTopicName() {
        assertEquals(EXPECTED_TOPIC, router.topic(), "El router debe devolver el nombre del topic configurado en el constructor");
    }
}