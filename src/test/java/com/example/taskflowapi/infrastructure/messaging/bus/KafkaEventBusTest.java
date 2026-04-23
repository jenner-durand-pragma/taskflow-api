package com.example.taskflowapi.infrastructure.messaging.bus;

import com.example.taskflowapi.infrastructure.messaging.contract.TopicRouter;
import com.example.taskflowapi.infrastructure.messaging.dto.EventMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventBusTest {

    @Mock
    private TopicRouter router1;

    @Mock
    private TopicRouter router2;

    @Mock
    private KafkaTemplate<String, EventMessage> kafkaTemplate;

    @Captor
    private ArgumentCaptor<EventMessage> messageCaptor;

    private KafkaEventBus kafkaEventBus;

    record DummyEvent(String data) {}

    @BeforeEach
    void setUp() {
        kafkaEventBus = new KafkaEventBus(List.of(router1, router2), kafkaTemplate);
    }

    @Test
    void sendWithThreeArgsShouldSendToKafkaWhenRouterSupportsEvent() {
        var event = new DummyEvent("data");
        var key = "123";
        var type = "DummyType";
        var targetTopic = "dummy-topic";

        when(router1.supports(event)).thenReturn(false);
        when(router2.supports(event)).thenReturn(true);
        when(router2.topic()).thenReturn(targetTopic);

        kafkaEventBus.send(event, key, type);

        verify(kafkaTemplate, times(1)).send(eq(targetTopic), eq(key), messageCaptor.capture());

        var capturedMessage = messageCaptor.getValue();
        assertEquals(key, capturedMessage.key());
        assertEquals(type, capturedMessage.type());
        assertEquals(event, capturedMessage.data());
    }

    @Test
    void sendWithThreeArgsShouldNotSendWhenNoRouterSupportsEvent() {
        var event = new DummyEvent("data");
        var key = "123";
        var type = "DummyType";

        when(router1.supports(event)).thenReturn(false);
        when(router2.supports(event)).thenReturn(false);

        kafkaEventBus.send(event, key, type);

        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void sendWithTwoArgsShouldDelegateToThreeArgsMethodWithNullType() {
        var event = new DummyEvent("data");
        var key = "123";
        var targetTopic = "dummy-topic";

        when(router1.supports(event)).thenReturn(true);
        when(router1.topic()).thenReturn(targetTopic);

        kafkaEventBus.send(event, key);

        verify(kafkaTemplate, times(1)).send(eq(targetTopic), eq(key), messageCaptor.capture());

        var capturedMessage = messageCaptor.getValue();
        assertEquals(key, capturedMessage.key());
        assertEquals(event, capturedMessage.data());
        assertNull(capturedMessage.type(), "El type debería ser null al usar el método de 2 argumentos");

        verifyNoInteractions(router2);
    }
}