package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.enums.ProjectStatus;
import com.example.taskflowapi.domain.event.project.ProjectCreatedDomainEvent;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends Model {
    private String code;
    private String name;
    private ProjectStatus status;
    private String description;

    public static Project create(String code, String name, String description) {
        if (Objects.isNull(code) || code.isBlank()) {
            code = generateCode(name);
        }

        var project = Project.builder()
                .code(code)
                .name(name)
                .description(description)
                .status(ProjectStatus.DRAFT)
                .build();

        project.raiseCreatedEvent();

        return project;
    }

    public void update(String name, String description, ProjectStatus status) {
    }

    public static Project create(String name, String description) {
        return create(null, name, description);
    }

    private static String generateCode(String name) {
        var prefix = name != null && name.length() >= 3 ? name.substring(0, 3).toUpperCase() : "PRJ";
        int randomNum = (int) (Math.random() * 10000);

        return "PROJ-" + prefix + "-" + randomNum;
    }

    public String getCodeWithSuffix(int attempt) {
        if (attempt == 0) return this.code;
        return this.code + "-" + attempt;
    }

    private void raiseCreatedEvent() {
        raise(new ProjectCreatedDomainEvent(code, name, description));
    }
}
