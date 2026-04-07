package com.example.taskflowapi.shared.mediator;

public interface Mediator {
    <D, T extends Request<D>> D send(T request);
}
