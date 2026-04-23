package com.example.taskflowapi.application.usecase.getprojects;

import com.example.taskflowapi.application.usecase.getprojects.GetProjectsQuery;
import com.example.taskflowapi.application.usecase.getprojects.GetProjectsQueryHandler;
import com.example.taskflowapi.domain.gateway.ProjectRepository;
import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.application.dto.pagination.PageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProjectsQueryHandlerTest {
    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private GetProjectsQueryHandler handler;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .name("Proyecto")
                .description("Ejemplo")
                .build();
    }

    @Test
    void handleShouldCallRepositoriesAndReturnListProjects() {
        var page = 0;
        var size = 10;
        var query = new GetProjectsQuery("Proyecto", PageRequest.of(page, size));

        var list = List.of(project);
        when(repository.searchPaginated(query.search(), query.pageRequest().page(), query.pageRequest().size())).thenReturn(list);
        when(repository.count(query.search())).thenReturn(1L);

        var result = handler.handle(query);

        assertNotNull(result);
        assertEquals(1, result.totalElements(), "El total de elementos debe ser 1");
        assertEquals(project.getId(), result.data().get(0).getId(), "El ID del primer elemento de la lista debe coincidir con el que se ha considerado");
    }
}
