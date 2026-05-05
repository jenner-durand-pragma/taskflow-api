package com.example.taskflowapi.application.exception.project;

import com.example.taskflowapi.domain.exception.base.NotFoundException;
import com.example.taskflowapi.domain.model.Project;

public class ProjectNotFoundException extends NotFoundException {
    private static final String ERROR_CODE = "ERR-PRJ-001";
    private static final String TEMPLATE_MESSAGE = "El proyecto con código '%s' no ha sido encontrado.";

    public ProjectNotFoundException(String code) {
        super(
                String.format(TEMPLATE_MESSAGE, code),
                ERROR_CODE,
                Project.class.getSimpleName(),
                code
        );
    }
}
