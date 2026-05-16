package com.taskmanagement.taskmanagement.dto.request;

import com.taskmanagement.taskmanagement.entity.TaskStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class TaskRequest {

    @NotBlank
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private TaskStatus status = TaskStatus.TODO;
    @FutureOrPresent (message = "Due date must not be in the past")
    private LocalDate dueDate;
}