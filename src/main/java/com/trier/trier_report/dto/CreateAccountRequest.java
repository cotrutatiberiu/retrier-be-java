package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull
        Long userId,
        @NotNull
        Long accountTypeId,
        @NotNull
        Long currencyId,

        @NotBlank
        String name
) {
}
