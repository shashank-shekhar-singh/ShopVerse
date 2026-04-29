package com.shopverse.userservice.dto;

import com.shopverse.userservice.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
