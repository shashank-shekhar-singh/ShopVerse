package com.shopverse.userservice.exception;

import java.util.UUID;

public class AddressNotFoundException extends BaseException {
    public AddressNotFoundException(UUID addressId) {
        super("Address not found with id: " + addressId, ErrorCode.ADDRESS_NOT_FOUND);
    }
}
