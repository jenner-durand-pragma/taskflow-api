package com.example.taskflowapi.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .code("TASK-001")
                .title("Prueba Tarea")
                .tags(new ArrayList<>())
                .build();
    }

    @Test
    void createTaskShouldCreateSuccessfully() {
        var tags = List.of(
            Tag.builder().code("TAG-001").name("Example").build(),
            Tag.builder().code("TAG-002").name("Example 2").build()
        );
        var task = Task.builder()
                .code("TASK-001")
                .title("Prueba Tarea")
                .tags(tags)
                .projectCode("PRJ-001")
                .build();

        var taskCreated = Task.create(task.getCode(), task.getTitle(), task.getProjectCode());

        assertNotNull(taskCreated);
        assertEquals(task.getCode(), taskCreated.getCode(), "Las tasks deben tener el mismo nombre");
        assertEquals(task.getProject(), taskCreated.getProject(), "Las tasks deben tener el mismo codigo de proyecto");
        assertEquals(1, taskCreated.getDomainEvents().size(), "El task debe contener solo 1 domain event");
    }

    @Test
    void addTagWhenTagIsNewShouldAddSuccessfully() {
        var tag = Tag.builder()
                .code("TAG-001")
                .name("Prueba Tag")
                .build();

        task.addTag(tag);

        assertEquals(1, task.getTags().size(), "La lista de tags deberia tener 1 tag");

        var tagAdded = task.getTags().get(0);

        assertEquals(tag.getCode(), tagAdded.getCode(), "El tagId debe coincidir con el ID del Tag");
    }

    @Test
    void addTagWhenTagAlreadyExistsShouldNotAddDuplicate() {
        var tag1 = new Tag();
        tag1.setCode("TAG-002");

        Tag duplicateTag = new Tag();
        duplicateTag.setCode("TAG-002");

        task.addTag(tag1);
        task.addTag(duplicateTag);

        assertEquals(1, task.getTags().size(), "La lista de tags no debería permitir duplicados");
        assertEquals(duplicateTag.getCode(), task.getTags().get(0).getCode());
    }

    @Test
    void addTagWithMultipleDistinctTagsShouldAddAll() {
        var tag1 = new Tag();
        tag1.setCode("TAG-003");

        var tag2 = new Tag();
        tag2.setCode("TAG-004");

        task.addTag(tag1);
        task.addTag(tag2);

        assertEquals(2, task.getTags().size(), "Debería haber añadido ambos tags correctamente");
        assertEquals(tag1.getCode(), task.getTags().get(0).getCode());
        assertEquals(tag2.getCode(), task.getTags().get(1).getCode());
    }
}
