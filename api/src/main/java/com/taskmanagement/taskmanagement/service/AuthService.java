package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.dto.request.LoginRequest;
import com.taskmanagement.taskmanagement.dto.request.SignupRequest;
import com.taskmanagement.taskmanagement.dto.response.LoginResponse;
import com.taskmanagement.taskmanagement.dto.response.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest request);
    LoginResponse login(LoginRequest request);
}