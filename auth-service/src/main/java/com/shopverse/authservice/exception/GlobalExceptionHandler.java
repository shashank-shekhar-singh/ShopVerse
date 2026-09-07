package com.shopverse.authservice.exception;

import com.shopverse.common.exception.AbstractGlobalExceptionHandler;
import com.shopverse.common.exception.ApiError;
import com.shopverse.common.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
            UserAlreadyExistsException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                .body(new ApiError(
                        "INVALID_CREDENTIALS",
                        "Invalid email or password",
                        org.springframework.http.HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI(),
                        null,
                        java.time.Instant.now()
                ));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> handleDisabledUser(
            DisabledException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        "ACCOUNT_DISABLED",
                        "User account is disabled",
                        org.springframework.http.HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        null,
                        java.time.Instant.now()
                ));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiError> handleLockedUser(
            LockedException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        "ACCOUNT_LOCKED",
                        "User account is locked",
                        org.springframework.http.HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        null,
                        java.time.Instant.now()
                ));
    }
}
