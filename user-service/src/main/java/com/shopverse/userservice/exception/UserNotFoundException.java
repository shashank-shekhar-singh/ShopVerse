package com.shopverse.userservice.exception;

import java.util.UUID;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(UUID id) {
        super("User not found with id: " + id, ErrorCode.USER_NOT_FOUND);
    }
}
