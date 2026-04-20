package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.dto.request.ProjectRequest;
import com.taskmanagement.taskmanagement.dto.response.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request, String email);
    List<ProjectResponse> getUserProjects(String email);
}