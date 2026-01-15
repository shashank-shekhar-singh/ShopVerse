package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record AddressRequest(
    @NotBlank
    AddressType type,
    boolean isDefault,
    @NotBlank
    String line1,
    @NotBlank
    String city,
    @NotBlank
    String state,
    @NotBlank
    String country,
    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode")
    String pincode
) {
}
