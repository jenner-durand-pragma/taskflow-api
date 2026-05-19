package com.example.taskflowapi.infrastructure.persistence.repository;

import com.example.taskflowapi.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByCodeIgnoreCase(String code);
}
