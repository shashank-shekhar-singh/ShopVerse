package com.shopverse.userservice.exception;

import jakarta.persistence.OptimisticLockException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

import static com.shopverse.userservice.exception.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String CORRELATION_ID_HEADER = "correlationId";

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiError> handleOptimisticLock() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        DATA_CONFLICT.name(),
                        "Data conflict occurred. Please retry.",
                        MDC.get(CORRELATION_ID_HEADER),
                        Instant.now()
                ));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex) {
        return buildResponseEntityForDomainExceptions(ex);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUser(
            DuplicateUserException ex) {
        return buildResponseEntityForDomainExceptions(ex);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiError> handleAddressNotFound(
            AddressNotFoundException ex) {
        return buildResponseEntityForDomainExceptions(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(
            MethodArgumentNotValidException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        VALIDATION_FAILED.name(),
                        ex.getBindingResult()
                                .getFieldErrors()
                                .get(0)
                                .getDefaultMessage(),
                        MDC.get(CORRELATION_ID_HEADER),
                        Instant.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        ErrorCode.INTERNAL_ERROR.name(),
                        "Unexpected error occurred",
                        MDC.get(CORRELATION_ID_HEADER),
                        Instant.now()
                ));
    }

    private ResponseEntity<ApiError> buildResponseEntityForDomainExceptions(
            BaseException ex) {
        return ResponseEntity
                .status(mapStatus(ex))
                .body(new ApiError(
                        ex.getErrorCode().name(),
                        ex.getMessage(),
                        MDC.get(CORRELATION_ID_HEADER),
                        Instant.now()
                ));
    }

    private HttpStatus mapStatus(BaseException ex) {
        return switch (ex.getErrorCode()) {
            case USER_NOT_FOUND, ADDRESS_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE_USER, DATA_CONFLICT -> HttpStatus.CONFLICT;
            case VALIDATION_FAILED -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
