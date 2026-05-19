package com.example.taskflowapi.infrastructure.persistence.mapper;

import com.example.taskflowapi.domain.model.Task;
import com.example.taskflowapi.infrastructure.persistence.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskEntityMapper {
    TaskEntity toEntity(Task model);
    Task toModel(TaskEntity entity);
}
