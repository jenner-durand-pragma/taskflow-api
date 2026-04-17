package com.example.taskflowapi.presentation.apirest.dto.common;

import com.example.taskflowapi.application.dto.pagination.PaginatedResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private Map<String, Object> meta;
    private Map<String, Object> links;

    public ApiResponse<T> withMessage(String message) {
        meta.put("message", message);

        return this;
    }

    public ApiResponse<T> withMessage(String message, String key) {
        if (!meta.containsKey(key)) {
            meta.put(key, message);
        }

        return this;
    }

    public static <T> ApiResponse<T> of(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> ApiResponse<List<T>> ofPaginated(PaginatedResult<T> paginatedResult) {
        Map<String, Object> paginationMeta = new HashMap<>();

        paginationMeta.put("totalPages", paginatedResult.totalPages());
        paginationMeta.put("totalElements", paginatedResult.totalElements());
        paginationMeta.put("currentPage", paginatedResult.currentPage());

        return ApiResponse.<List<T>>builder()
                .data(paginatedResult.data())
                .meta(paginationMeta)
                .build();
    }
}
