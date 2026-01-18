package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
    @NotBlank(message = "first name is required")
    String firstName,
    String lastName,
    @Pattern(
        regexp = "^[789]\\d{9}$",
        message = "phone number is not valid"
    )
    @NotBlank(message = "phone number is required")
    String phone,
    @Email(message = "email should be valid")
    @NotBlank(message = "email is required")
    String email,
    @NotNull(message = "gender is required")
    Gender gender
) {}