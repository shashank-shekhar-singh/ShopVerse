package com.shopverse.authservice.service;

import com.shopverse.authservice.domain.entity.AuthUser;
import com.shopverse.authservice.domain.enums.Role;
import com.shopverse.authservice.dto.AuthResponse;
import com.shopverse.authservice.dto.LoginRequest;
import com.shopverse.authservice.dto.SignupRequest;
import com.shopverse.authservice.repository.AuthUserRepository;
import com.shopverse.authservice.security.JwtUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        AuthUser user = new AuthUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() == null ? Role.USER : request.getRole());

        AuthUser saved = userRepository.save(user);

        UserDetails userDetails = User.withUsername(saved.getUsername())
                .password(saved.getPassword())
                .roles(saved.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, saved.getUsername(), saved.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        AuthUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        UserDetails userDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, user.getUsername(), user.getRole());
    }
}

