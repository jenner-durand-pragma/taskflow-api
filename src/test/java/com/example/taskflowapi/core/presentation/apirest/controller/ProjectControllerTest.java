package com.example.taskflowapi.core.presentation.apirest.controller;

import com.example.taskflowapi.core.application.createproject.CreateProjectCommand;
import com.example.taskflowapi.core.application.getprojects.GetProjectsQuery;
import com.example.taskflowapi.core.domain.model.Project;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectRequest;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectResponse;
import com.example.taskflowapi.core.presentation.apirest.dto.getprojects.GetProjectsRequest;
import com.example.taskflowapi.core.presentation.apirest.dto.getprojects.GetProjectsResponse;
import com.example.taskflowapi.core.presentation.apirest.mapper.ProjectRequestMapper;
import com.example.taskflowapi.shared.common.dto.pagination.PaginatedResult;
import com.example.taskflowapi.shared.mediator.Mediator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @Mock
    private ProjectRequestMapper mapper;

    @InjectMocks
    private ProjectController controller;

    @Test
    void createProjectShouldReturnOkAndApiResponse() {
        var project = Project.builder()
                .id(1L)
                .name("Proyecto")
                .description("Ejemplo")
                .build();
        var request = new CreateProjectRequest(project.getName(), project.getDescription());
        var command = new CreateProjectCommand(request.name(), request.description());
        var response = new CreateProjectResponse(project.getId(), project.getName(), project.getDescription());

        when(mapper.toCommand(request)).thenReturn(command);
        when(mediator.send(command)).thenReturn(project);
        when(mapper.toResponse(project)).thenReturn(response);

        var result = controller.createProject(request);

        assertNotNull(result);

        verify(mapper).toCommand(request);
        verify(mediator).send(command);
        verify(mapper).toResponse(project);
    }

    @Test
    void getProjectsShouldReturnOkAndPaginatedApiResponse() {
        var page = 1;
        var size = 10;
        var totalElements = 1;
        var project = Project.builder()
                .id(1L)
                .name("Proyecto")
                .description("Ejemplo")
                .build();
        var projectResponse = new GetProjectsResponse(project.getId(), project.getName(), project.getDescription());
        var request = new GetProjectsRequest("Proyecto", page, size);
        var query = request.toQuery();
        var list = List.of(project);
        var response = PaginatedResult.of(list, totalElements, page, size);

        when(mediator.send(query)).thenReturn(response);
        when(mapper.toResponseList(project)).thenReturn(projectResponse);

        var result = controller.getProjects(request);
        assertNotNull(result);

        var bodyResult = result.getBody();
        assertNotNull(bodyResult);

        assertEquals(1, bodyResult.getData().size(), "El cuerpo de la respuesta debe tener 1 elemento");

        verify(mediator).send(query);
        verify(mapper).toResponseList(project);
    }
}
