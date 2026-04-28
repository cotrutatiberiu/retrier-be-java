package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotNull;

public record AccountArchiveDTO(
        @NotNull
        boolean archived
) {
}
