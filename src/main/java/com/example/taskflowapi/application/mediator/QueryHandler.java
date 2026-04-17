package com.example.taskflowapi.application.mediator;

public interface QueryHandler<D extends Query<T>, T> {
    T handle(D query);
}
