package com.shopverse.productservice.exception;

import com.shopverse.common.exception.AbstractGlobalExceptionHandler;
import com.shopverse.common.exception.ApiError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<ApiError> handleProductPurchase(
            ProductPurchaseException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request) {
        return buildResponseForDomainException(ex, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "PRODUCT_NOT_FOUND",
                        ex.getMessage(),
                        org.springframework.http.HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        null,
                        java.time.Instant.now()
                ));
    }
}
