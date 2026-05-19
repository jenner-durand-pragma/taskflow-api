package com.example.taskflowapi.infrastructure.persistence.mapper;

import com.example.taskflowapi.domain.model.Task;
import com.example.taskflowapi.infrastructure.persistence.entity.TagEntity;
import com.example.taskflowapi.infrastructure.persistence.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskEntityMapper {
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "tags", ignore = true)
    TaskEntity toEntity(Task model);

    @Mapping(source = "project.code", target = "projectCode")
    @Mapping(source = "tags", target = "tagCodes")
    Task toModel(TaskEntity entity);

    default List<String> mapTagEntitiesToTagCodes(Set<TagEntity> tags) {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
        return tags.stream()
                .map(TagEntity::getCode)
                .collect(Collectors.toList());
    }
}
