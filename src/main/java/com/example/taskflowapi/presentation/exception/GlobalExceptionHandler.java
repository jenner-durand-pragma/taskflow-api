package com.example.taskflowapi.presentation.exception;

import com.example.taskflowapi.presentation.apirest.dto.common.ApiError;
import com.example.taskflowapi.presentation.apirest.dto.common.ApiErrorResponse;
import com.example.taskflowapi.domain.exception.base.BusinessRuleException;
import com.example.taskflowapi.domain.exception.base.ConflictException;
import com.example.taskflowapi.domain.exception.base.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String JSON_API_CONTENT_TYPE = "application/vnd.api+json";

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessRuleExceptions(BusinessRuleException ex) {
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var error = new ApiError(
                UUID.randomUUID().toString(),
                status.value(),
                "BUSINESS_RULE_ERROR",
                "Business Rule Exception",
                ex.getMessage(),
                ex.getField().isEmpty() ? null : Map.of("pointer", "/data/attributes/" + ex.getField())
        );

        return buildResponse(List.of(error), status);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflictException(ConflictException ex) {
        var status = HttpStatus.CONFLICT;
        var error = new ApiError(
                UUID.randomUUID().toString(),
                status.value(),
                "CONFLICT_ERROR",
                "Conflict ocurred",
                ex.getMessage(),
                ex.getField().isEmpty() ? null : Map.of("pointer", "/data/attributes/" + ex.getField())
        );

        return buildResponse(List.of(error), status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        var error = new ApiError(
                UUID.randomUUID().toString(),
                status.value(),
                "NOT_FOUND_ERROR",
                "Resource not found",
                ex.getMessage(),
                null
        );

        return buildResponse(List.of(error), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var status = HttpStatus.BAD_REQUEST;
        var errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();

                    return new ApiError(
                            UUID.randomUUID().toString(),
                            status.value(),
                            "VALIDATION_ERROR",
                            "Invalid Attribute",
                            errorMessage,
                            // JSON:API usa punteros para indicar qué campo falló
                            Map.of("pointer", "/data/attributes/" + fieldName)
                    );
                })
                .toList();

        return buildResponse(errors, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var error = new ApiError(
                UUID.randomUUID().toString(),
                status.value(),
                "BUSINESS_RULE_VIOLATION",
                "Unprocessable Entity",
                ex.getMessage(),
                null
        );

        return buildResponse(List.of(error), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllOtherExceptions(Exception ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var error = new ApiError(
                UUID.randomUUID().toString(),
                status.value(),
                "INTERNAL_SERVER_ERROR",
                "Internal Server Error",
                "Ha ocurrido un error inesperado. Por favor, contacte a soporte técnico.",
                null
        );

        return buildResponse(List.of(error), status);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(List<ApiError> errors, HttpStatus status) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(JSON_API_CONTENT_TYPE));

        return new ResponseEntity<>(new ApiErrorResponse(errors, null), headers, status);
    }
}
