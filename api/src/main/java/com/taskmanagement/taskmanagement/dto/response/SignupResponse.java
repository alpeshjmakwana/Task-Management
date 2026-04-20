package com.taskmanagement.taskmanagement.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Builder @AllArgsConstructor
public class SignupResponse {
    private Long id;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
}