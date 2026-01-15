package com.shopverse.userservice.exception;

public class DuplicateUserException extends BaseException{
    public DuplicateUserException(String email) {
        super("User already exists with this email", ErrorCode.DUPLICATE_USER);
    }
}
