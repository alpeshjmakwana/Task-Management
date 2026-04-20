package com.taskmanagement.taskmanagement.dto.response;

import com.taskmanagement.taskmanagement.entity.TaskStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Builder @AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}