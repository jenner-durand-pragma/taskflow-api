package com.example.taskflowapi.domain.model;

import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String code;
    private String title;
    private Long projectId;
    private Long userId;
    private List<Tag> tags;

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
