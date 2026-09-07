package com.shopverse.userservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

public class DuplicateUserException extends BaseException {

    public DuplicateUserException(String email) {
        super("User already exists with email: " + email, ErrorCode.DUPLICATE_USER);
    }
}
