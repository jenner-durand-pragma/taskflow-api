package com.example.taskflowapi.shared.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Setter
public abstract class Result<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Map<String, Object> meta;

    protected Result<T> withMeta(Map<String, Object> meta) {
        if(Objects.nonNull(meta) && !meta.isEmpty()) {
            this.setMeta(meta);
        }

        return this;
    }

    public static <T> Result<T> success(T value, Map<String, Object> meta) {
        var result = new Success<>(value);

        return result.withMeta(meta);
    }

    public static <T> Result<T> failure(T message, String errorCode, Map<String, Object> meta) {
        var result = new Failure<>(message, errorCode);

        return result.withMeta(meta);
    }
}