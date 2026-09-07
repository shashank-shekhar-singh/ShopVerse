package com.shopverse.authservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String email) {
        super("User already exists with this email: " + email, ErrorCode.USER_ALREADY_EXISTS);
    }
}
