package com.example.taskflowapi.shared.mediator;

public interface QueryHandler<D extends Query<T>, T> {
    T handle(D query);
}
