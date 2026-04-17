package com.example.taskflowapi.application.dto.pagination;

import java.util.List;
import java.util.function.Function;

public record PaginatedResult<T>(
        List<T> data,
        int totalPages,
        long totalElements,
        int currentPage
) {
    public static <T> PaginatedResult<T> of(
            List<T> data,
            long totalElements,
            int currentPage,
            int pageSize
    ) {
        int calculatedTotalPages = pageSize > 0 ? (int) Math.ceil((double) totalElements / pageSize) : 0;

        return new PaginatedResult<>(data, calculatedTotalPages, totalElements, currentPage);
    }

    public <R> PaginatedResult<R> mapTo(Function<T, R> mapper) {
        var mappedData = this.data.stream()
                .map(mapper)
                .toList();

        return new PaginatedResult<>(
                mappedData,
                this.totalPages,
                this.totalElements,
                this.currentPage
        );
    }
}