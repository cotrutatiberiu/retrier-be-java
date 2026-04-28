package com.trier.trier_report.dto;

import java.time.Instant;

public record AccountResponseDTO(
        Long id,
        Long userId,
        Long currencyId,
        String name,
        boolean archived,
        Instant createdAt,
        Instant updatedAt
) {
}
