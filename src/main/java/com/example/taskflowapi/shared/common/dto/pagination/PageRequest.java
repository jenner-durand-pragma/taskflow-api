package com.example.taskflowapi.shared.common.dto.pagination;

public record PageRequest(
        Integer page,
        Integer size
) {
    public static PageRequest of(Integer page, Integer size) {
        var realPage = page > 0 ? page - 1 : 0;

        return new PageRequest(realPage, size);
    }
}
