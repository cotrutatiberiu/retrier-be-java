package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotNull;

public record AccountArchive(
        @NotNull
        boolean archived
) {
}
