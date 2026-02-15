package com.trier.trier_report.dto;

import com.trier.trier_report.entity.User;

public record UserResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        User.Role role
) {
}
