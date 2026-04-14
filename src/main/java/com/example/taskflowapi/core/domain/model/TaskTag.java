package com.example.taskflowapi.core.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTag {
    private Long taskId;
    private Long tagId;

    private Task task;
    private Tag tag;
}
