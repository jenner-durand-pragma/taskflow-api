package com.example.taskflowapi.application.mediator;

public interface CommandHandler<D extends Command<T>, T> {
    T handle(D command);
}
