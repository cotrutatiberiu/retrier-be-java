package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull
        long userId,
        @NotNull
        long accountTypeId,
        @NotNull
        long currencyId,

        @NotBlank
        String name
) {
}
