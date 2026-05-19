package com.example.taskflowapi.domain.gateway;

import com.example.taskflowapi.domain.model.Task;

import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);
    Optional<Task> findByCode(String code);
}
