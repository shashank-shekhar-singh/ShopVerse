package com.shopverse.userservice.service;

import com.shopverse.userservice.domain.entity.User;
import com.shopverse.userservice.dto.UserRequest;
import com.shopverse.userservice.dto.UserResponse;
import com.shopverse.userservice.dto.UserUpdateRequest;
import com.shopverse.userservice.exception.DuplicateUserException;
import com.shopverse.userservice.exception.UserNotFoundException;
import com.shopverse.userservice.mapper.UserMapper;
import com.shopverse.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        if(!userRequest.email().isBlank()) {
            userRepository.findByEmail(userRequest.email())
                    .ifPresent(user -> {
                        throw new DuplicateUserException(userRequest.email());
                    });
        }
        User user = userRepository.save(userMapper.toEntity(userRequest));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserResponse get(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponse update(UUID id, UserUpdateRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
