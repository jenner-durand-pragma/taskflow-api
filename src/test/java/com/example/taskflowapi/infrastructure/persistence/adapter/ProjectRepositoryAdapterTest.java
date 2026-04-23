package com.example.taskflowapi.infrastructure.persistence.adapter;

import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.infrastructure.persistence.entity.ProjectEntity;
import com.example.taskflowapi.infrastructure.persistence.mapper.ProjectEntityMapper;
import com.example.taskflowapi.infrastructure.persistence.repository.ProjectEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectRepositoryAdapterTest {

    @Mock
    private ProjectEntityRepository repository;

    private final ProjectEntityMapper mapper = Mappers.getMapper(ProjectEntityMapper.class);

    private ProjectRepositoryAdapter adapter;

    @Captor
    private ArgumentCaptor<ProjectEntity> entityCaptor;

    private Project model;
    private ProjectEntity entity;

    @BeforeEach
    void setUp() {
        adapter = new ProjectRepositoryAdapter(repository, mapper);

        model = Project.builder()
                .id(1L)
                .name("Proyecto de prueba")
                .description("Descripcion de Prueba")
                .build();

        entity = ProjectEntity.builder()
                .id(1L)
                .name("Proyecto de prueba")
                .description("Descripcion de Prueba")
                .build();
    }

    @Test
    void createShouldSetDatesAndSaveEntity() {
        when(repository.save(any(ProjectEntity.class))).thenReturn(entity);

        var result = adapter.create(model);

        assertNotNull(result);
        verify(repository).save(entityCaptor.capture());

        var resultCaptor = entityCaptor.getValue();

        assertNotNull(resultCaptor.getCreatedAt());
        assertNotNull(resultCaptor.getUpdatedAt());
        assertEquals(model.getName(), resultCaptor.getName());
    }

    @Test
    void updateShouldSetUpdatedAtAndSaveEntity() {
        when(repository.save(any(ProjectEntity.class))).thenReturn(entity);

        var result = adapter.update(model);

        assertNotNull(result);
        verify(repository).save(entityCaptor.capture());

        var resultCaptor = entityCaptor.getValue();

        assertNotNull(resultCaptor.getUpdatedAt());
        assertEquals(model.getName(), resultCaptor.getName());
    }

    @Test
    void searchPaginatedWithNullOrBlankSearchShouldCallFindAllBy() {
        var page = 0;
        var size = 10;
        var search = "";
        var pageable = PageRequest.of(page, size);
        var entities = List.of(entity);

        when(repository.findAllBy(pageable)).thenReturn(entities);

        var result = adapter.searchPaginated(search, page, size);

        assertEquals(1, result.size());
        assertEquals(entity.getId(), result.get(0).getId());
        verify(repository).findAllBy(pageable);
        verify(repository, never()).findByNameOrDescriptionContainingIgnoreCase(search, search, pageable);
    }

    @Test
    void searchPaginatedWithValidSearchTermShouldCallFindByNameOrDescription() {
        var page = 0;
        var size = 10;
        var search = "Proyecto de prueba";
        var pageable = PageRequest.of(page, size);
        var entities = List.of(entity);

        when(repository.findByNameOrDescriptionContainingIgnoreCase(search, search, pageable)).thenReturn(entities);

        var result = adapter.searchPaginated(search, page, size);

        assertEquals(1, result.size());
        assertEquals(entity.getId(), result.get(0).getId());
        verify(repository).findByNameOrDescriptionContainingIgnoreCase(search, search, pageable);
        verify(repository, never()).findAllBy(pageable);
    }

    @Test
    void countWithNullOrBlankSearchShouldCallCount() {
        when(repository.count()).thenReturn(5L);

        var result = adapter.count(null);

        assertEquals(5L, result);
        verify(repository).count();
        verify(repository, never()).countByNameOrDescriptionContainingIgnoreCase(any(), any());
    }

    @Test
    void countWithValidSearchTermShouldCallCountByNameOrDescription() {
        var search = "Proyecto de prueba";
        when(repository.countByNameOrDescriptionContainingIgnoreCase(search, search)).thenReturn(1L);

        var result = adapter.count(search);

        assertEquals(1L, result);
        verify(repository).countByNameOrDescriptionContainingIgnoreCase(search, search);
        verify(repository, never()).count();
    }
}