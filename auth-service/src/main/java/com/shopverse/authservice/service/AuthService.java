package com.shopverse.authservice.service;

import com.shopverse.authservice.dto.AuthResponse;
import com.shopverse.authservice.dto.LoginRequest;
import com.shopverse.authservice.dto.RefreshTokenRequest;
import com.shopverse.authservice.dto.RegisterRequest;
import com.shopverse.authservice.entity.Role;
import com.shopverse.authservice.entity.User;
import com.shopverse.authservice.exception.UserAlreadyExistsException;
import com.shopverse.authservice.repository.RoleRepository;
import com.shopverse.authservice.repository.UserRepository;
import com.shopverse.authservice.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String TOKEN_TYPE = "Bearer";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(
                    email
            );
        }

        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException(
                        "Default role is not configured"
                ));

        User user = new User();

        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        user.getRoles().add(defaultRole);

        User savedUser = userRepository.save(user);

        CustomUserDetails userDetails =
                new CustomUserDetails(savedUser);

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        String refreshToken =
                refreshTokenService.createRefreshToken(savedUser);

        return buildAuthResponse(
                accessToken,
                refreshToken
        );
    }

    public AuthResponse login(LoginRequest request) {

        String email = normalizeEmail(request.getEmail());

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                request.getPassword()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        String refreshToken =
                refreshTokenService.createRefreshToken(user);

        return buildAuthResponse(
                accessToken,
                refreshToken
        );
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {

        String rawRefreshToken =
                request.getRefreshToken();

        User user =
                refreshTokenService.validateAndGetUser(
                        rawRefreshToken
                );

        // Rotate the refresh token.
        refreshTokenService.revokeToken(
                rawRefreshToken
        );

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String newAccessToken =
                jwtService.generateAccessToken(userDetails);

        String newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return buildAuthResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {

        refreshTokenService.revokeToken(
                request.getRefreshToken()
        );
    }

    private AuthResponse buildAuthResponse(
            String accessToken,
            String refreshToken
    ) {

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpirationMs(),
                TOKEN_TYPE
        );
    }

    private String normalizeEmail(String email) {

        if (email == null) {
            return null;
        }

        return email.trim().toLowerCase();
    }
}
