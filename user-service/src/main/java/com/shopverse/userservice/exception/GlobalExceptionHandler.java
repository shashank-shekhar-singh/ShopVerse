package com.shopverse.userservice.exception;

import com.shopverse.common.exception.AbstractGlobalExceptionHandler;
import com.shopverse.common.exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUser(
            DuplicateUserException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiError> handleAddressNotFound(
            AddressNotFoundException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }
}
