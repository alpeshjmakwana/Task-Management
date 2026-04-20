package com.taskmanagement.taskmanagement.dto.request;

import com.taskmanagement.taskmanagement.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateTaskStatusRequest {

    @NotNull(message = "Status is required. Values: TODO, IN_PROGRESS, DONE")
    private TaskStatus status;
}