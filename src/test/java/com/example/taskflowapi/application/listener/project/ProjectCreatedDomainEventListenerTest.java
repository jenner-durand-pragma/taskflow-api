package com.example.taskflowapi.application.listener.project;

import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class ProjectCreatedDomainEventListenerTest {

    private final ProjectCreatedDomainEventListener listener = new ProjectCreatedDomainEventListener();

    @Test
    void shouldLogWhenEventIsReceived(CapturedOutput output) {
        var event = new ProjectCreatedDomainEvent("PROJ-001", "Proyecto 001", "Ejemplo");

        listener.on(event);

        assertThat(output.getOut()).contains(event.code());
    }
}