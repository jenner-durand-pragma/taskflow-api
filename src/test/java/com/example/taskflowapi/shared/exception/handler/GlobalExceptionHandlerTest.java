package com.example.taskflowapi.shared.exception.handler;

import com.example.taskflowapi.presentation.exception.GlobalExceptionHandler;
import com.example.taskflowapi.domain.exception.BusinessRuleException;
import com.example.taskflowapi.domain.exception.ConflictException;
import com.example.taskflowapi.domain.exception.NotFoundException;
import com.example.taskflowapi.presentation.apirest.dto.common.ApiError;
import com.example.taskflowapi.presentation.apirest.dto.common.ApiErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleBusinessRuleExceptionsWithFieldShouldReturn422() {
        var ex = mock(BusinessRuleException.class);
        when(ex.getMessage()).thenReturn("Invalid transition");
        when(ex.getField()).thenReturn(Optional.of("status"));

        var response = exceptionHandler.handleBusinessRuleExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("BUSINESS_RULE_ERROR");
        assertThat(error.detail()).isEqualTo("Invalid transition");
    }

    @Test
    void handleConflictExceptionShouldReturn409() {
        var ex = mock(ConflictException.class);
        when(ex.getMessage()).thenReturn("Email already exists");
        when(ex.getField()).thenReturn(Optional.of("email"));

        var response = exceptionHandler.handleConflictException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("CONFLICT_ERROR");
        assertThat(error.title()).isEqualTo("Conflict ocurred");
    }

    @Test
    void handleNotFoundExceptionShouldReturn404() {
        var ex = mock(NotFoundException.class);
        when(ex.getMessage()).thenReturn("User not found");

        var response = exceptionHandler.handleNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("NOT_FOUND_ERROR");
        assertThat(error.detail()).isEqualTo("User not found");
    }

    @Test
    void handleValidationExceptionsShouldReturn400() {
        var ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("userDto", "username", "must not be blank");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        var response = exceptionHandler.handleValidationExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("VALIDATION_ERROR");
        assertThat(error.detail()).isEqualTo("must not be blank");
    }

    @Test
    void handleIllegalArgumentExceptionShouldReturn422() {
        var ex = new IllegalArgumentException("Invalid argument passed");

        var response = exceptionHandler.handleIllegalArgumentException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("BUSINESS_RULE_VIOLATION");
        assertThat(error.detail()).isEqualTo("Invalid argument passed");
    }

    @Test
    void handleAllOtherExceptionsShouldReturn500() {
        var ex = new Exception("Database connection timeout");

        var response = exceptionHandler.handleAllOtherExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertHeadersContentType(response);

        var error = getFirstError(response);
        assertThat(error.code()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(error.detail()).isEqualTo("Ha ocurrido un error inesperado. Por favor, contacte a soporte técnico.");
    }

    private void assertHeadersContentType(ResponseEntity<ApiErrorResponse> response) {
        assertThat(Objects.requireNonNull(response.getHeaders().getContentType()).toString())
                .isEqualTo("application/vnd.api+json");
    }

    private ApiError getFirstError(ResponseEntity<ApiErrorResponse> response) {
        assertThat(response.getBody()).isNotNull();

        var errors = response.getBody().errors();
        assertThat(errors).isNotEmpty();

        return errors.get(0);
    }
}