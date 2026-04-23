package com.example.taskflowapi.domain.exception.base;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends AppException {
    private final String resource;
    private final Object resourceId;

    protected NotFoundException(String message, String errorCode, String resource, Object resourceId) {
        super(message, errorCode);
        this.resource = resource;
        this.resourceId = resourceId;
    }
}
