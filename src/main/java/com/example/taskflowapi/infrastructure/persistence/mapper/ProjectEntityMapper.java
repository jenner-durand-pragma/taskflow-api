package com.example.taskflowapi.infrastructure.persistence.mapper;

import com.example.taskflowapi.domain.model.Project;
import com.example.taskflowapi.infrastructure.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectEntityMapper {
    ProjectEntity toEntity(Project model);
    Project toModel(ProjectEntity entity);
}
