package com.taskmanagement.taskmanagement.repository;

import com.taskmanagement.taskmanagement.entity.Project;
import com.taskmanagement.taskmanagement.entity.Task;
import com.taskmanagement.taskmanagement.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByProject(Project project, Pageable pageable);
    Page<Task> findByProjectAndStatus(Project project, TaskStatus status, Pageable pageable);
    Optional<Task> findByIdAndProject_Owner_Email(Long id, String ownerEmail);
}