package com.example.taskflowapi.infrastructure.persistence.adapter;

import com.example.taskflowapi.application.exception.common.ReferencedResourceNotFoundException;
import com.example.taskflowapi.domain.enums.TaskStatus;
import com.example.taskflowapi.domain.model.Task;
import com.example.taskflowapi.infrastructure.persistence.entity.ProjectEntity;
import com.example.taskflowapi.infrastructure.persistence.entity.TaskEntity;
import com.example.taskflowapi.infrastructure.persistence.mapper.TaskEntityMapper;
import com.example.taskflowapi.infrastructure.persistence.repository.ProjectEntityRepository;
import com.example.taskflowapi.infrastructure.persistence.repository.TaskEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryAdapterTest {

    @Mock
    private TaskEntityRepository taskRepository;

    @Mock
    private ProjectEntityRepository projectRepository;

    private final TaskEntityMapper mapper = Mappers.getMapper(TaskEntityMapper.class);

    private TaskRepositoryAdapter adapter;

    @Captor
    private ArgumentCaptor<TaskEntity> entityCaptor;

    private Task model;
    private TaskEntity entity;
    private ProjectEntity projectEntity;

    @BeforeEach
    void setUp() {
        adapter = new TaskRepositoryAdapter(taskRepository, projectRepository, mapper);

        model = Task.builder()
                .code("TASK-001")
                .title("Example Task")
                .status(TaskStatus.PENDING_APPROVAL)
                .projectCode("PRJ-1234")
                .build();

        projectEntity = ProjectEntity.builder()
                .id(1L)
                .code("PRJ-1234")
                .name("Project Name")
                .build();

        entity = TaskEntity.builder()
                .id(1L)
                .code("TASK-001")
                .title("Example Task")
                .userId(12L)
                .status(TaskStatus.PENDING_APPROVAL)
                .project(projectEntity)
                .build();
    }

    @Test
    void createShouldFetchProjectAndSaveEntity() {
        when(projectRepository.findByCodeIgnoreCase(model.getProjectCode()))
                .thenReturn(Optional.of(projectEntity));

        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(invocation -> {
            var entityPushedToRepo = (TaskEntity) invocation.getArgument(0);
            entityPushedToRepo.setId(1L);
            entityPushedToRepo.setCreatedAt(LocalDateTime.now());
            entityPushedToRepo.setUpdatedAt(LocalDateTime.now());

            return entityPushedToRepo;
        });

        var result = adapter.create(model);

        assertNotNull(result);
        verify(projectRepository).findByCodeIgnoreCase(model.getProjectCode());
        verify(taskRepository).save(entityCaptor.capture());

        var resultCaptor = entityCaptor.getValue();
        assertEquals(model.getCode(), resultCaptor.getCode());
        assertEquals(model.getTitle(), resultCaptor.getTitle());
        assertNotNull(resultCaptor.getProject());
        assertEquals(projectEntity.getCode(), resultCaptor.getProject().getCode());
    }

    @Test
    void createShouldThrowExceptionWhenProjectNotFound() {
        when(projectRepository.findByCodeIgnoreCase(model.getProjectCode()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(ReferencedResourceNotFoundException.class, () -> adapter.create(model));
        assertEquals(model.getProjectCode(), exception.getKeyValue());

        verify(taskRepository, never()).save(any());
    }

    @Test
    void findByCodeShouldReturnSuccessfully() {
        var code = "TASK-001";
        when(taskRepository.findByCodeIgnoreCase(code)).thenReturn(Optional.of(entity));

        var result = adapter.findByCode(code);
        var resultModel = result.orElse(null);

        assertNotNull(resultModel, "La tarea debe existir");
        assertEquals(code, resultModel.getCode(), "El codigo debe ser el mismo");
        assertEquals(entity.getProject().getCode(), resultModel.getProjectCode(), "Debe mapear el projectCode");
    }

    @Test
    void findByCodeShouldReturnEmptyIfNotExists() {
        var code = "TASK-000";
        when(taskRepository.findByCodeIgnoreCase(code)).thenReturn(Optional.empty());

        var result = adapter.findByCode(code);

        assertTrue(result.isEmpty(), "La tarea no debe existir");
    }
}