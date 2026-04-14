package com.example.taskflowapi.core.presentation.apirest.mapper;

import com.example.taskflowapi.core.application.createproject.CreateProjectCommand;
import com.example.taskflowapi.core.domain.model.Project;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectRequest;
import com.example.taskflowapi.core.presentation.apirest.dto.createproject.CreateProjectResponse;
import com.example.taskflowapi.core.presentation.apirest.dto.getprojects.GetProjectsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectRequestMapper {
    CreateProjectCommand toCommand(CreateProjectRequest request);
    CreateProjectResponse toResponse(Project project);

    GetProjectsResponse toResponseList(Project project);
}
