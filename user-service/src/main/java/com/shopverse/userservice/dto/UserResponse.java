package com.shopverse.userservice.dto;

import com.shopverse.userservice.domain.enums.AccountStatus;
import com.shopverse.userservice.domain.enums.Gender;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Gender gender,
        AccountStatus status
) {}