package com.example.taskflowapi.infrastructure.persistence.repository;

import com.example.taskflowapi.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {
    @EntityGraph(attributePaths = {"tags", "project"})
    Optional<TaskEntity> findByCodeIgnoreCase(String code);
}
