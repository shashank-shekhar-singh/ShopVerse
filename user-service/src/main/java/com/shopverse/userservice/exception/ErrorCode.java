package com.shopverse.userservice.exception;

public enum ErrorCode {
    // USER errors
    USER_NOT_FOUND,
    DUPLICATE_USER,

    // Address errors
    ADDRESS_NOT_FOUND,

    // Validation / generic
    VALIDATION_FAILED,
    DATA_CONFLICT,
    INTERNAL_ERROR;

}
