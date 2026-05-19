package com.example.taskflowapi.application.exception.common;

import lombok.Getter;

@Getter
public class ReferencedResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String keyName;
    private final String keyValue;

    public ReferencedResourceNotFoundException(String resourceName, String keyName, String keyValue) {
        super(String.format("Recurso referenciado no encontrado: [%s] con %s = '%s'", resourceName, keyName, keyValue));
        this.resourceName = resourceName;
        this.keyName = keyName;
        this.keyValue = keyValue;
    }
}
