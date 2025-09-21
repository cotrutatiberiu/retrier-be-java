package com.trier.trier_report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank String email,
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password
) {
}
