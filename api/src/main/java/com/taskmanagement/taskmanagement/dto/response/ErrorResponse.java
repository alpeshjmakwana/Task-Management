package com.taskmanagement.taskmanagement.dto.response;

import lombok.*;
import java.time.Instant;

@Getter @Builder @AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}