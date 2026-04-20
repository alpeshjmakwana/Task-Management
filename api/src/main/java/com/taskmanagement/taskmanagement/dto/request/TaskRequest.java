package com.taskmanagement.taskmanagement.dto.request;

import com.taskmanagement.taskmanagement.entity.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class TaskRequest {

    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private TaskStatus status = TaskStatus.TODO;

    private LocalDate dueDate;
}