package com.example.taskflowapi.core.presentation.apirest.controller;

import com.example.taskflowapi.shared.common.dto.ApiResponse;
import com.example.taskflowapi.shared.mediator.Mediator;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectRequest;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectResponse;
import com.example.taskflowapi.core.presentation.apirest.dto.getprojects.GetProjectsRequest;
import com.example.taskflowapi.core.presentation.apirest.dto.getprojects.GetProjectsResponse;
import com.example.taskflowapi.core.presentation.apirest.mapper.ProjectRequestMapper;
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
