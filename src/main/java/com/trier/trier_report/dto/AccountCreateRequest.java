package com.trier.trier_report.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreateRequest(
        @NotNull
        Long userId,
        @NotNull
        Long accountTypeId,
        @NotNull
        Long currencyId,

        @NotBlank
        @Size(min = 3, max = 20, message = "Name should be at least 3 characters long")
        String name
) {
}
