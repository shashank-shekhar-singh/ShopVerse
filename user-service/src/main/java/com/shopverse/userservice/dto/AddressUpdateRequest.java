package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.AddressType;

public record AddressUpdateRequest(
        AddressType type,
        Boolean isDefault,
        String line1,
        String city,
        String state,
        String country,
        String pincode
) {}
