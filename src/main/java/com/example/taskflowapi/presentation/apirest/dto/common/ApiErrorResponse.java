package com.example.taskflowapi.presentation.apirest.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public record ApiErrorResponse(
        List<ApiError> errors,
        @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, Object> meta
) {}
