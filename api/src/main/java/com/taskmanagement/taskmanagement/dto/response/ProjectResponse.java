package com.taskmanagement.taskmanagement.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Builder @AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String ownerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}