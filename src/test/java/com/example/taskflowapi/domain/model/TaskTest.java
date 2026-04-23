package com.example.taskflowapi.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .title("Prueba Tarea")
                .tags(new ArrayList<>())
                .build();
    }

    @Test
    void addTagWhenTagIsNewShouldAddSuccessfully() {
        var tag = Tag.builder()
                .id(2L)
                .name("Prueba Tag")
                .build();

        task.addTag(tag);

        assertEquals(1, task.getTags().size(), "La lista de tags deberia tener 1 tag");

        var tagAdded = task.getTags().get(0);

        assertEquals(2L, tagAdded.getTagId(), "El tagId debe coincidir con el ID del Tag");
        assertEquals(1L, tagAdded.getTaskId(), "El taskId debe coincidir con el ID del Task");
        assertEquals(tag, tagAdded.getTag(), "La referencia al objeto Tag debe ser correcta");
        assertEquals(task, tagAdded.getTask(), "La referencia al objeto Task debe ser correcta");
    }

    @Test
    void addTagWhenTagAlreadyExistsShouldNotAddDuplicate() {
        var tag1 = new Tag();
        tag1.setId(1L);

        Tag duplicateTag = new Tag();
        duplicateTag.setId(1L);

        task.addTag(tag1);
        task.addTag(duplicateTag);

        assertEquals(1, task.getTags().size(), "La lista de tags no debería permitir duplicados");
        assertEquals(1L, task.getTags().get(0).getTagId());
    }

    @Test
    void addTagWithMultipleDistinctTagsShouldAddAll() {
        var tag1 = new Tag();
        tag1.setId(1L);

        var tag2 = new Tag();
        tag2.setId(2L);

        task.addTag(tag1);
        task.addTag(tag2);

        assertEquals(2, task.getTags().size(), "Debería haber añadido ambos tags correctamente");
        assertEquals(1L, task.getTags().get(0).getTagId());
        assertEquals(2L, task.getTags().get(1).getTagId());
    }
}
