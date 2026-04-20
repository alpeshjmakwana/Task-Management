package com.taskmanagement.taskmanagement.dto.response;

import lombok.*;

@Getter @Builder @AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tokenType;
    private Long expiresIn;
}