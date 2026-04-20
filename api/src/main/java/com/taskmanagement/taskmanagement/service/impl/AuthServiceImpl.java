package com.taskmanagement.taskmanagement.service.impl;

import com.taskmanagement.taskmanagement.dto.request.LoginRequest;
import com.taskmanagement.taskmanagement.dto.request.SignupRequest;
import com.taskmanagement.taskmanagement.dto.response.LoginResponse;
import com.taskmanagement.taskmanagement.dto.response.SignupResponse;
import com.taskmanagement.taskmanagement.entity.User;
import com.taskmanagement.taskmanagement.exception.DuplicateResourceException;
import com.taskmanagement.taskmanagement.repository.UserRepository;
import com.taskmanagement.taskmanagement.security.JwtUtil;
import com.taskmanagement.taskmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use.");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();
        User saved = userRepository.save(user);
        return SignupResponse.builder()
                .id(saved.getId()).email(saved.getEmail())
                .fullName(saved.getFullName()).createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return LoginResponse.builder()
                .token(token).tokenType("Bearer").expiresIn(jwtUtil.getExpirationMs())
                .build();
    }
}