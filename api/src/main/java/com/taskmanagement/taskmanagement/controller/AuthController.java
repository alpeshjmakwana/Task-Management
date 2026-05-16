package com.taskmanagement.taskmanagement.controller;

import com.taskmanagement.taskmanagement.dto.request.LoginRequest;
import com.taskmanagement.taskmanagement.dto.request.SignupRequest;
import com.taskmanagement.taskmanagement.dto.response.LoginResponse;
import com.taskmanagement.taskmanagement.dto.response.SignupResponse;
import com.taskmanagement.taskmanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}