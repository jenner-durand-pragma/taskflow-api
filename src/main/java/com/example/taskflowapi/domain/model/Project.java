package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends Model<Long> {
    private Long id;
    private String name;
    private String description;

    public static Project create(String name, String description) {
        return Project.builder()
                .name(name)
                .description(description)
                .build();
    }

    public void raiseCreatedEvent() {
        raise(new ProjectCreatedDomainEvent(id, name, description));
    }
}
