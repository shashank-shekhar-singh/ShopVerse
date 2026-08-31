package com.shopverse.authservice.exception;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String email) {
        super("User already exists with this email: " + email, ErrorCode.DUPLICATE_USER);
    }
}
