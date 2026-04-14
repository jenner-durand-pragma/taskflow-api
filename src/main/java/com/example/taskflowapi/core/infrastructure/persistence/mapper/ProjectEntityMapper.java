package com.example.taskflowapi.core.infrastructure.persistence.mapper;

import com.example.taskflowapi.core.domain.model.Project;
import com.example.taskflowapi.core.infrastructure.persistence.entity.ProjectEntity;
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
