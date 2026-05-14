package com.example.taskflowapi.domain.model;

import com.example.taskflowapi.domain.enums.TaskStatus;
import com.example.taskflowapi.domain.event.task.TaskCreatedDomainEvent;
import lombok.*;

import java.util.ArrayList;
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
    private TaskStatus status;

    private String projectCode;
    private List<String> tagCodes = new ArrayList<>();

    public static Task create(String code, String title, String projectCode) {
        var task = Task.builder()
                .code(code)
                .title(title)
                .status(TaskStatus.PENDING_APPROVAL)
                .projectCode(projectCode)
                .build();

        var domainEvent = new TaskCreatedDomainEvent(task.getCode(), task.getTitle(), task.getProjectCode());
        task.raise(domainEvent);

        return task;
    }

    public void addTag(String tagCode) {
        if (tagCodes.contains(tagCode)) {
            return;
        }
        tagCodes.add(tagCode);
    }
}
