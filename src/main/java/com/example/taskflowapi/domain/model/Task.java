package com.example.taskflowapi.domain.model;

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
        return null;
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
