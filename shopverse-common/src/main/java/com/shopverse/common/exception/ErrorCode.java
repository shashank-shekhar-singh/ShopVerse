package com.shopverse.common.exception;

/**
 * Unified error codes across all ShopVerse microservices.
 * Each service uses only the codes relevant to its domain.
 */
public enum ErrorCode {

    // ========== User Service Errors ==========
    USER_NOT_FOUND,
    DUPLICATE_USER,
    ADDRESS_NOT_FOUND,

    // ========== Auth Service Errors ==========
    USER_ALREADY_EXISTS,
    INVALID_CREDENTIALS,
    ACCOUNT_DISABLED,
    ACCOUNT_LOCKED,
    INVALID_TOKEN,
    TOKEN_EXPIRED,

    // ========== Product Service Errors ==========
    PRODUCT_NOT_FOUND,
    INSUFFICIENT_STOCK,
    INVALID_PRODUCT,

    // ========== Order Service Errors ==========
    ORDER_NOT_FOUND,
    ORDER_ALREADY_CANCELLED,
    INVALID_ORDER_STATE,

    // ========== Payment Service Errors ==========
    PAYMENT_FAILED,
    PAYMENT_TIMEOUT,
    PAYMENT_ALREADY_PROCESSED,

    // ========== Common / Generic Errors ==========
    VALIDATION_FAILED,
    DATA_CONFLICT,
    INVALID_REQUEST,
    INTERNAL_ERROR
}
