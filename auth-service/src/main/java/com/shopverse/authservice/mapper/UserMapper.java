package com.shopverse.authservice.mapper;

import com.shopverse.authservice.entity.Role;
import com.shopverse.authservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserMapper {

    public Map<String, Object> toProfile(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "roles", roles,
                "enabled", user.isEnabled()
        );
    }
}
