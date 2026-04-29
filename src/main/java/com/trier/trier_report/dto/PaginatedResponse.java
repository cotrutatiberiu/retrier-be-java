package com.trier.trier_report.dto;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> list,
        int pageNumber,
        int pageSize,
        int totalPages,
        Long totalElements
) {
}
