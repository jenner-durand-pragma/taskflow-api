package com.example.taskflowapi.infrastructure.publisher;

import com.example.taskflowapi.domain.event.DomainEvent;
import com.example.taskflowapi.domain.model.Model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringDomainEventPublisherTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private SpringDomainEventPublisher springDomainEventPublisher;

    @Mock
    private Model<?> model1;

    @Mock
    private Model<?> model2;

    @Mock
    private DomainEvent<?> event1;

    @Mock
    private DomainEvent<?> event2;

    @Mock
    private DomainEvent<?> event3;

    @Test
    void publishSingleModelShouldPublishAllItsEvents() {
        doReturn(List.of(event1, event2)).when(model1).getDomainEvents();

        springDomainEventPublisher.publish(model1);

        verify(applicationEventPublisher, times(1)).publishEvent(event1);
        verify(applicationEventPublisher, times(1)).publishEvent(event2);

        verifyNoMoreInteractions(applicationEventPublisher);
    }

    @Test
    void publishMultipleModelsShouldPublishAllEventsFromAllModels() {
        doReturn(List.of(event1)).when(model1).getDomainEvents();
        doReturn(List.of(event2, event3)).when(model2).getDomainEvents();

        springDomainEventPublisher.publish(model1, model2);
        verify(applicationEventPublisher, times(1)).publishEvent(event1);
        verify(applicationEventPublisher, times(1)).publishEvent(event2);
        verify(applicationEventPublisher, times(1)).publishEvent(event3);
        verifyNoMoreInteractions(applicationEventPublisher);
    }

    @Test
    void publishModelWithoutEventsShouldNotPublishAnything() {
        doReturn(List.of()).when(model1).getDomainEvents();

        springDomainEventPublisher.publish(model1);

        verifyNoInteractions(applicationEventPublisher);
    }
}