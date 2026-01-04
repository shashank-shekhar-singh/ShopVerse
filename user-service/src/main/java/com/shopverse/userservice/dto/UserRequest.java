package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotBlank
    String firstName,
    String lastName,
    @NotBlank
    String phone,
    @Email
    @NotBlank
    String email,
    @NotNull
    Gender gender
) {}