package com.example.taskflowapi.presentation.apirest.dto.createproject;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @Nullable
        @Size(min = 3, max = 10, message = "El codigo debe tener entre 3 y 10 caracteres")
        String code,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String name,

        @Size(max = 511, message = "La descripcion tiene un maximo de 511 caracteres")
        String description
) {
}
