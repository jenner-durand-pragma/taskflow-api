package com.example.taskflowapi.shared.exception.handler;

import com.example.taskflowapi.shared.exception.base.BusinessRuleException;
import com.example.taskflowapi.shared.exception.base.ConflictException;
import com.example.taskflowapi.shared.exception.base.NotFoundException;
import com.example.taskflowapi.shared.exception.dto.ApiError;
import com.example.taskflowapi.shared.exception.dto.ApiErrorResponse;
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
    void handleBusinessRuleExceptions_WithField_ShouldReturn422() {
        // Arrange
        BusinessRuleException ex = mock(BusinessRuleException.class);
        when(ex.getMessage()).thenReturn("Invalid transition");
        when(ex.getField()).thenReturn(Optional.of("status"));

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleBusinessRuleExceptions(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("BUSINESS_RULE_ERROR");
        assertThat(error.detail()).isEqualTo("Invalid transition");
    }

    @Test
    void handleConflictException_ShouldReturn409() {
        // Arrange
        ConflictException ex = mock(ConflictException.class);
        when(ex.getMessage()).thenReturn("Email already exists");
        when(ex.getField()).thenReturn(Optional.of("email"));

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleConflictException(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("CONFLICT_ERROR");
        assertThat(error.title()).isEqualTo("Conflict ocurred");
    }

    @Test
    void handleNotFoundException_ShouldReturn404() {
        // Arrange
        NotFoundException ex = mock(NotFoundException.class);
        when(ex.getMessage()).thenReturn("User not found");

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleNotFoundException(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("NOT_FOUND_ERROR");
        assertThat(error.detail()).isEqualTo("User not found");
    }

    @Test
    void handleValidationExceptions_ShouldReturn400() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("userDto", "username", "must not be blank");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("VALIDATION_ERROR");
        assertThat(error.detail()).isEqualTo("must not be blank");
    }

    @Test
    void handleIllegalArgumentException_ShouldReturn422() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument passed");

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleIllegalArgumentException(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("BUSINESS_RULE_VIOLATION");
        assertThat(error.detail()).isEqualTo("Invalid argument passed");
    }

    @Test
    void handleAllOtherExceptions_ShouldReturn500() {
        // Arrange
        Exception ex = new Exception("Database connection timeout");

        // Act
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleAllOtherExceptions(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertHeadersContentType(response);

        ApiError error = getFirstError(response);
        assertThat(error.code()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(error.detail()).isEqualTo("Ha ocurrido un error inesperado. Por favor, contacte a soporte técnico.");
    }

    private void assertHeadersContentType(ResponseEntity<ApiErrorResponse> response) {
        assertThat(Objects.requireNonNull(response.getHeaders().getContentType()).toString())
                .isEqualTo("application/vnd.api+json");
    }

    private ApiError getFirstError(ResponseEntity<ApiErrorResponse> response) {
        assertThat(response.getBody()).isNotNull();
        List<ApiError> errors = response.getBody().errors();
        assertThat(errors).isNotEmpty();

        return errors.get(0);
    }
}