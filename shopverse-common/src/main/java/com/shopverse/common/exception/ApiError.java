package com.shopverse.common.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Standardized API error response structure used across all ShopVerse microservices.
 */
public record ApiError(
        String code,
        String message,
        int status,
        String path,
        String correlationId,
        Instant timestamp,
        Map<String, String> validationErrors
) {
    /**
     * Constructor for standard errors without validation details.
     */
    public ApiError(String code, String message, int status, String path, String correlationId, Instant timestamp) {
        this(code, message, status, path, correlationId, timestamp, null);
    }
}
