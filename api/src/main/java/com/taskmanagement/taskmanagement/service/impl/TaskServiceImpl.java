package com.taskmanagement.taskmanagement.service.impl;

import com.taskmanagement.taskmanagement.dto.request.TaskRequest;
import com.taskmanagement.taskmanagement.dto.response.PagedResponse;
import com.taskmanagement.taskmanagement.dto.response.TaskResponse;
import com.taskmanagement.taskmanagement.entity.*;
import com.taskmanagement.taskmanagement.exception.*;
import com.taskmanagement.taskmanagement.repository.*;
import com.taskmanagement.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskResponse createTask(Long projectId, TaskRequest request, String email) {
        User user = getUser(email);
        Project project = projectRepository.findByIdAndOwner(projectId, user)
                .orElseThrow(() -> new ForbiddenException(
                        "Access denied: You do not own project with id: " + projectId));
        Task task = Task.builder()
                .title(request.getTitle()).description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .dueDate(request.getDueDate()).project(project).build();
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TaskResponse> getTasks(Long projectId, int page, int size,
                                                TaskStatus status, String email) {
        User user = getUser(email);
        Project project = projectRepository.findByIdAndOwner(projectId, user)
                .orElseThrow(() -> new ForbiddenException(
                        "Access denied: You do not own project with id: " + projectId));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> taskPage = status != null
                ? taskRepository.findByProjectAndStatus(project, status, pageable)
                : taskRepository.findByProject(project, pageable);
        List<TaskResponse> content = taskPage.getContent()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
        return PagedResponse.<TaskResponse>builder()
                .content(content).pageNumber(taskPage.getNumber())
                .totalElements(taskPage.getTotalElements()).totalPages(taskPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public TaskResponse updateStatus(Long taskId, TaskStatus status, String email) {
        Task task = taskRepository.findByIdAndProject_Owner_Email(taskId, email)
                .orElseThrow(() -> new ForbiddenException(
                        "Access denied: Task not found or you do not own it."));
        task.setStatus(status);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, String email) {
        Task task = taskRepository.findByIdAndProject_Owner_Email(taskId, email)
                .orElseThrow(() -> new ForbiddenException(
                        "Access denied: Task not found or you do not own it."));
        taskRepository.delete(task);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    private TaskResponse mapToResponse(Task t) {
        return TaskResponse.builder()
                .id(t.getId()).title(t.getTitle()).description(t.getDescription())
                .status(t.getStatus()).dueDate(t.getDueDate())
                .projectId(t.getProject().getId())
                .createdAt(t.getCreatedAt()).updatedAt(t.getUpdatedAt()).build();
    }
}