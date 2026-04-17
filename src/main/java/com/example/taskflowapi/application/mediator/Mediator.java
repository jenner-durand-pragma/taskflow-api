package com.example.taskflowapi.application.mediator;

public interface Mediator {
    <D, T extends Request<D>> D send(T request);
}
