package com.example.taskflowapi.presentation.apirest.controller;

import com.example.taskflowapi.application.usecase.createproject.CreateProjectCommand;
import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.presentation.apirest.dto.createproject.CreateProjectRequest;
import com.example.taskflowapi.presentation.apirest.dto.getprojects.GetProjectsRequest;
import com.example.taskflowapi.presentation.apirest.dto.getprojects.GetProjectsResponse;
import com.example.taskflowapi.presentation.apirest.mapper.ProjectRequestMapper;
import com.example.taskflowapi.application.dto.pagination.PaginatedResult;
import com.example.taskflowapi.application.mediator.Mediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private Mediator mediator;

    private final ProjectRequestMapper mapper = Mappers.getMapper(ProjectRequestMapper.class);

    private ProjectController controller;

    @BeforeEach
    void setUp() {
        controller = new ProjectController(mediator, mapper);
    }

    @Test
    void createProjectShouldReturnOkAndApiResponse() {
        var project = Project.builder()
                .code("PRJ-1234")
                .name("Proyecto")
                .description("Ejemplo")
                .build();
        var request = new CreateProjectRequest(project.getCode(), project.getName(), project.getDescription());

        when(mediator.send(any(CreateProjectCommand.class))).thenReturn(project);

        var result = controller.createProject(request);

        assertNotNull(result);

        verify(mediator).send(any(CreateProjectCommand.class));
    }

    @Test
    void getProjectsShouldReturnOkAndPaginatedApiResponse() {
        var page = 1;
        var size = 10;
        var totalElements = 1;
        var project = Project.builder()
                .code("PRJ-1234")
                .name("Proyecto")
                .description("Ejemplo")
                .build();

        var request = new GetProjectsRequest("Proyecto", page, size);
        var query = request.toQuery();
        var list = List.of(project);
        var response = PaginatedResult.of(list, totalElements, page, size);

        when(mediator.send(query)).thenReturn(response);

        var result = controller.getProjects(request);

        assertNotNull(result);
        var bodyResult = result.getBody();
        assertNotNull(bodyResult);

        assertEquals(1, bodyResult.getData().size(), "El cuerpo de la respuesta debe tener 1 elemento");

        var firstItem = (GetProjectsResponse) bodyResult.getData().get(0);
        assertEquals(project.getCode(), firstItem.code());
        assertEquals(project.getName(), firstItem.name());

        verify(mediator).send(query);
    }
}