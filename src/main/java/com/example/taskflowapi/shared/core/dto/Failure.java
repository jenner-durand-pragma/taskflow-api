package com.example.taskflowapi.shared.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Failure<T> extends Result<T> {
    private T message;
    private String errorCode;
}
