package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.AddressType;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        UUID customerId,
        AddressType type,
        boolean isDefault,
        String line1,
        String city,
        String state,
        String country,
        String pincode
) {
}
