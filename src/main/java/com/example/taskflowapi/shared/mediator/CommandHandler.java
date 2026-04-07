package com.example.taskflowapi.shared.mediator;

public interface CommandHandler<D extends Command<T>, T> {
    T handle(D command);
}
