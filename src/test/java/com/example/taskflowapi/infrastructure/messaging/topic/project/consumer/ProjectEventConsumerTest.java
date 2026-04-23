package com.example.taskflowapi.infrastructure.messaging.topic.project.consumer;

import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import com.example.taskflowapi.infrastructure.messaging.topic.project.handler.ProjectEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectEventConsumerTest {

    @Mock
    private ProjectEventHandler<DummyEvent> handler1;

    @Mock
    private ProjectEventHandler<DummyEvent> handler2;

    @Mock
    private ObjectMapper mapper;

    private ProjectEventConsumer consumer;

    record DummyEvent(String name) {}

    @BeforeEach
    void setUp() {
        consumer = new ProjectEventConsumer(List.of(handler1, handler2), mapper);
    }

    @Test
    void consumeShouldInvokeCorrectHandlerWhenEventIsSupported() {
        var rawData = new Object();
        var eventMessage = new EventMessage("key-1", "DUMMY_EVENT", rawData);
        var expectedParsedEvent = new DummyEvent("Test");

        when(handler1.supports("DUMMY_EVENT")).thenReturn(true);
        doReturn(DummyEvent.class).when(handler1).getEventClass();
        when(mapper.convertValue(rawData, DummyEvent.class)).thenReturn(expectedParsedEvent);

        consumer.consume(eventMessage);

        verify(handler1, times(1)).handle(expectedParsedEvent);
        verify(handler2, never()).handle(any());
    }

    @Test
    void consumeShouldNotInvokeAnyHandlerWhenEventTypeIsUnknown() {
        var eventMessage = new EventMessage("key-1", "UNKNOWN_EVENT", new Object());

        when(handler1.supports("UNKNOWN_EVENT")).thenReturn(false);
        when(handler2.supports("UNKNOWN_EVENT")).thenReturn(false);

        consumer.consume(eventMessage);

        verify(handler1, never()).handle(any());
        verify(handler2, never()).handle(any());
        verifyNoInteractions(mapper);
    }

    @Test
    void consumeShouldCatchExceptionWhenMapperFailsToConvertValue() {
        var rawData = new Object();
        var eventMessage = new EventMessage("key-1", "DUMMY_EVENT", rawData);

        when(handler1.supports("DUMMY_EVENT")).thenReturn(true);
        doReturn(DummyEvent.class).when(handler1).getEventClass();
        when(mapper.convertValue(rawData, DummyEvent.class))
                .thenThrow(new IllegalArgumentException("Error de parseo simulado"));

        consumer.consume(eventMessage);

        verify(handler1, never()).handle(any());
    }
}