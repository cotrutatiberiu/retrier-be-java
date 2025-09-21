package com.trier.trier_report.dto;

import com.trier.trier_report.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        @NotBlank
        String email,
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotBlank
        User.Role role
) {
}
