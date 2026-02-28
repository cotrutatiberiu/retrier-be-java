package com.trier.trier_report.dto;

public record UserResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        long roleId
) {
}
