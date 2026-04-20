package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.dto.request.TaskRequest;
import com.taskmanagement.taskmanagement.dto.response.PagedResponse;
import com.taskmanagement.taskmanagement.dto.response.TaskResponse;
import com.taskmanagement.taskmanagement.entity.TaskStatus;

public interface TaskService {
    TaskResponse createTask(Long projectId, TaskRequest request, String email);
    PagedResponse<TaskResponse> getTasks(Long projectId, int page, int size, TaskStatus status, String email);
    TaskResponse updateStatus(Long taskId, TaskStatus status, String email);
    void deleteTask(Long taskId, String email);
}