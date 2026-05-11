package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.event.task.TaskCreatedDomainEvent;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task extends Model {
    private String code;
    private String title;
    private List<Tag> tags;

    private String projectCode;
    private Project project;

    public static Task create(String code, String title, String projectCode) {
        var task = Task.builder()
                .code(code)
                .title(title)
                .projectCode(projectCode)
                .build();

        var domainEvent = new TaskCreatedDomainEvent(task.getCode(), task.getTitle(), task.getProjectCode());
        task.raise(domainEvent);

        return task;
    }

    public void addTag(Tag tag) {
        if (searchTag(tag).isPresent()) {
            return;
        }

        tags.add(tag);
    }

    private Optional<Tag> searchTag(Tag tag) {
        return tags.stream().filter(
                t -> tag.getCode().equals(t.getCode())
        ).findFirst();
    }
}
