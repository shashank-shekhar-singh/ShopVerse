package com.shopverse.userservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

import java.util.UUID;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(UUID userId) {
        super("User not found with id: " + userId, ErrorCode.USER_NOT_FOUND);
    }
}
