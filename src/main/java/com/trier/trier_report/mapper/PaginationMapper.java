package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PaginationMapper {
    public static <T> PaginatedResponse<T> toDto(Page<T> page) {
        return new PaginatedResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
    }

    public static <E, D> PaginatedResponse<D> toDto(Page<E> page, Function<E, D> mapper) {
        return new PaginatedResponse<>(page.map(mapper).getContent(), page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
    }
}
