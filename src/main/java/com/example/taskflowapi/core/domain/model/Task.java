package com.example.taskflowapi.core.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private Long projectId;
    private Long userId;
    private List<TaskTag> tags;

    public void addTag(Tag tag) {
        if (searchTag(tag).isPresent()) {
            return;
        }

        tags.add(
                TaskTag.builder()
                        .tagId(tag.getId())
                        .taskId(id)
                        .tag(tag)
                        .task(this)
                        .build()
        );
    }

    private Optional<TaskTag> searchTag(Tag tag) {
        return tags.stream().filter(
                t -> tag.getId().equals(t.getTagId())
        ).findFirst();
    }
}
