package com.example.taskflowapi.presentation.apirest.controller;

import com.example.taskflowapi.presentation.apirest.dto.common.ApiResponse;
import com.example.taskflowapi.application.mediator.Mediator;
import com.example.taskflowapi.presentation.apirest.dto.createproject.CreateProjectRequest;
import com.example.taskflowapi.presentation.apirest.dto.createproject.CreateProjectResponse;
import com.example.taskflowapi.presentation.apirest.dto.getprojects.GetProjectsRequest;
import com.example.taskflowapi.presentation.apirest.dto.getprojects.GetProjectsResponse;
import com.example.taskflowapi.presentation.apirest.mapper.ProjectRequestMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {
    private final Mediator mediator;
    private final ProjectRequestMapper mapper;

    /*
    * Only for test purposes, because it must be created in Appian
    * */
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProjectResponse>> createProject(
            @Valid
            @RequestBody
            CreateProjectRequest request
    ) {
        var command = mapper.toCommand(request);
        var project = mediator.send(command);
        var projectResponse = mapper.toResponse(project);
        var response = ApiResponse.of(projectResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetProjectsResponse>>> getProjects(
            @Valid
            @ModelAttribute
            GetProjectsRequest request
    ) {
        var query = request.toQuery();
        var projects = mediator.send(query);
        var projectsResponse = projects.mapTo(mapper::toResponseList);
        var response = ApiResponse.ofPaginated(projectsResponse);

        return ResponseEntity.ok(response);
    }
}
