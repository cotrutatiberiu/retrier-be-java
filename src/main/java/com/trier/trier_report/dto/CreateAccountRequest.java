package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(
        @NotBlank
        long userId,
        @NotBlank
        long accountTypeId,
        @NotBlank
        String name,
        @NotBlank
        long currencyId
) {
}
