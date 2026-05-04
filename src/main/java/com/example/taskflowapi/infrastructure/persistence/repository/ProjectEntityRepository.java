package com.example.taskflowapi.infrastructure.persistence.repository;

import com.example.taskflowapi.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.ListQueryByExampleExecutor;

import java.util.List;

public interface ProjectEntityRepository extends
        JpaRepository<ProjectEntity, Long>,
        PagingAndSortingRepository<ProjectEntity, Long>,
        ListQueryByExampleExecutor<ProjectEntity> {
    List<ProjectEntity> findByCodeOrNameOrDescriptionContainingIgnoreCase(String code, String name, String description, Pageable pageable);
    List<ProjectEntity> findAllBy(Pageable pageable);

    Long countByCodeOrNameOrDescriptionContainingIgnoreCase(String code, String name, String description);

    Boolean existsByCodeIgnoreCase(String code);
}
