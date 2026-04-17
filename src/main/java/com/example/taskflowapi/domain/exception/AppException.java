package com.example.taskflowapi.domain.exception;

import lombok.Getter;

public abstract class AppException extends RuntimeException {
    @Getter
    private final String errorCode;

    protected AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
