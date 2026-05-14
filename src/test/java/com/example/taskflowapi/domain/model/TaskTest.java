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
                .tagCodes(new ArrayList<>())
                .build();
    }

    @Test
    void createTaskShouldCreateSuccessfully() {
        var tagCodes = List.of("TAG-001", "TAG-002");

        var expectedTask = Task.builder()
                .code("TASK-001")
                .title("Prueba Tarea")
                .tagCodes(tagCodes)
                .projectCode("PRJ-001")
                .build();

        var taskCreated = Task.create(expectedTask.getCode(), expectedTask.getTitle(), expectedTask.getProjectCode());

        assertNotNull(taskCreated);
        assertEquals(expectedTask.getCode(), taskCreated.getCode(), "Las tasks deben tener el mismo codigo");
        assertEquals(expectedTask.getProjectCode(), taskCreated.getProjectCode(), "Las tasks deben tener el mismo codigo de proyecto");
        assertEquals(1, taskCreated.getDomainEvents().size(), "El task debe contener solo 1 domain event");
    }

    @Test
    void addTagWhenTagIsNewShouldAddSuccessfully() {
        var tagCode = "TAG-001";

        task.addTag(tagCode);

        assertEquals(1, task.getTagCodes().size(), "La lista de tags deberia tener 1 tag");

        var tagAdded = task.getTagCodes().get(0);

        assertEquals(tagCode, tagAdded, "El tagCode debe coincidir con el código insertado");
    }

    @Test
    void addTagWhenTagAlreadyExistsShouldNotAddDuplicate() {
        var tagCode = "TAG-002";

        task.addTag(tagCode);
        task.addTag(tagCode);

        assertEquals(1, task.getTagCodes().size(), "La lista de tags no debería permitir duplicados");
        assertEquals(tagCode, task.getTagCodes().get(0));
    }

    @Test
    void addTagWithMultipleDistinctTagsShouldAddAll() {
        var tagCode1 = "TAG-003";
        var tagCode2 = "TAG-004";

        task.addTag(tagCode1);
        task.addTag(tagCode2);

        assertEquals(2, task.getTagCodes().size(), "Debería haber añadido ambos tags correctamente");
        assertEquals(tagCode1, task.getTagCodes().get(0));
        assertEquals(tagCode2, task.getTagCodes().get(1));
    }
}