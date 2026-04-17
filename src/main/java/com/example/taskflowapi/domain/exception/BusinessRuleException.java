package com.example.taskflowapi.domain.exception;

import java.util.Optional;

public abstract class BusinessRuleException extends AppException {
    private final String field;

    protected BusinessRuleException(String message, String errorCode, String field) {
        super(message, errorCode);
        this.field = field;
    }

    public Optional<String> getField() {
        return Optional.ofNullable(field);
    }
}
