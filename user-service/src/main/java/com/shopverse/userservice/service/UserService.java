package com.shopverse.userservice.service;

import com.shopverse.userservice.dto.UserRequest;
import com.shopverse.userservice.dto.UserResponse;
import com.shopverse.userservice.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse get(UUID id);
    Page<UserResponse> getAll(Pageable pageable);
    UserResponse update(UUID id, UserUpdateRequest userRequest);
    void delete(UUID id);
}
