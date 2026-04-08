package com.example.taskflowapi.shared.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        String id,          // Identificador único de esta ocurrencia del problema (útil para logs)
        Integer status,      // El código HTTP en string (ej. "400")
        String code,        // Código interno de tu aplicación (ej. "ERR-USER-001")
        String title,       // Resumen corto en humano (ej. "Invalid Attribute")
        String detail,      // Explicación detallada (ej. "El email debe tener un formato válido")
        Map<String, String> source // Puntero a la propiedad que falló (ej. {"pointer": "/data/attributes/email"})
) {}