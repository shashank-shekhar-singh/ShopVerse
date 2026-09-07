package com.shopverse.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.shopverse.common.config.CorrelationIdFilter.CORRELATION_ID_LOG_KEY;

/**
 * Base exception handler providing common error handling across all microservices.
 * Services should extend this class and add service-specific exception handlers.
 */
public abstract class AbstractGlobalExceptionHandler {

    /**
     * Handle validation errors from @Valid annotations.
     * Returns a map of field names to error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        ErrorCode.VALIDATION_FAILED.name(),
                        "Request validation failed",
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now(),
                        validationErrors
                ));
    }

    /**
     * Handle malformed JSON in request body.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        ErrorCode.VALIDATION_FAILED.name(),
                        "Malformed JSON request",
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now()
                ));
    }

    /**
     * Handle optimistic locking conflicts.
     */
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiError> handleOptimisticLock(
            OptimisticLockException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        ErrorCode.DATA_CONFLICT.name(),
                        "Data conflict occurred. Please retry.",
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now()
                ));
    }

    /**
     * Handle IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        ErrorCode.INVALID_REQUEST.name(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now()
                ));
    }

    /**
     * Catch-all handler for unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        ErrorCode.INTERNAL_ERROR.name(),
                        "An unexpected error occurred",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now()
                ));
    }

    /**
     * Build response for domain exceptions extending BaseException.
     */
    protected ResponseEntity<ApiError> buildResponseForDomainException(
            BaseException ex,
            HttpServletRequest request) {

        HttpStatus status = mapStatus(ex.getErrorCode());

        return ResponseEntity
                .status(status)
                .body(new ApiError(
                        ex.getErrorCode().name(),
                        ex.getMessage(),
                        status.value(),
                        request.getRequestURI(),
                        MDC.get(CORRELATION_ID_LOG_KEY),
                        Instant.now()
                ));
    }

    /**
     * Maps ErrorCode to appropriate HTTP status.
     * Override in subclasses to add service-specific mappings.
     */
    protected HttpStatus mapStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            // User Service
            case USER_NOT_FOUND, ADDRESS_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE_USER -> HttpStatus.CONFLICT;

            // Auth Service
            case USER_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
            case ACCOUNT_DISABLED, ACCOUNT_LOCKED -> HttpStatus.FORBIDDEN;
            case INVALID_TOKEN, TOKEN_EXPIRED -> HttpStatus.UNAUTHORIZED;

            // Product Service
            case PRODUCT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INSUFFICIENT_STOCK, INVALID_PRODUCT -> HttpStatus.BAD_REQUEST;

            // Order Service
            case ORDER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ORDER_ALREADY_CANCELLED, INVALID_ORDER_STATE -> HttpStatus.BAD_REQUEST;

            // Payment Service
            case PAYMENT_FAILED, PAYMENT_TIMEOUT -> HttpStatus.PAYMENT_REQUIRED;
            case PAYMENT_ALREADY_PROCESSED -> HttpStatus.CONFLICT;

            // Common
            case VALIDATION_FAILED, INVALID_REQUEST -> HttpStatus.BAD_REQUEST;
            case DATA_CONFLICT -> HttpStatus.CONFLICT;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
