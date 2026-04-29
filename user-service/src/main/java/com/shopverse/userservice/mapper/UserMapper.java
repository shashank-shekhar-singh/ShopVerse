package com.shopverse.userservice.mapper;

import com.shopverse.userservice.entity.User;
import com.shopverse.userservice.dto.UserRequest;
import com.shopverse.userservice.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest request);
    UserResponse toResponseDTO(User user);
}
