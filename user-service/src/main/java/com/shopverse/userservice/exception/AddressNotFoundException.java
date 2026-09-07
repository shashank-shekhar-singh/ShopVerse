package com.shopverse.userservice.exception;

import com.shopverse.common.exception.BaseException;
import com.shopverse.common.exception.ErrorCode;

import java.util.UUID;

public class AddressNotFoundException extends BaseException {

    public AddressNotFoundException(UUID addressId) {
        super("Address not found with id: " + addressId, ErrorCode.ADDRESS_NOT_FOUND);
    }
}
