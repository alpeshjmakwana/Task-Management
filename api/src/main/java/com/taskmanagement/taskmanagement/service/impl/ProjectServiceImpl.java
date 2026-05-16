package com.taskmanagement.taskmanagement.service.impl;

import com.taskmanagement.taskmanagement.dto.request.ProjectRequest;
import com.taskmanagement.taskmanagement.dto.response.ProjectResponse;
import com.taskmanagement.taskmanagement.entity.Project;
import com.taskmanagement.taskmanagement.entity.User;
import com.taskmanagement.taskmanagement.exception.ResourceNotFoundException;
import com.taskmanagement.taskmanagement.repository.ProjectRepository;
import com.taskmanagement.taskmanagement.repository.UserRepository;
import com.taskmanagement.taskmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, String email) {
        User owner = getUser(email);
        if(owner ==null){
            throw new RuntimeException("user not found");
        }
        Project project = Project.builder()
                .name(request.getName()).description(request.getDescription()).owner(owner).build();
        return mapToResponse(projectRepository.save(project));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getUserProjects(String email) {
        return projectRepository.findByOwner(getUser(email))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    private ProjectResponse mapToResponse(Project p) {
        return ProjectResponse.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .ownerEmail(p.getOwner().getEmail())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt()).build();
    }
}