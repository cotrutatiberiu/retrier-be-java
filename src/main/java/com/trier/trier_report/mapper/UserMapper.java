package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.UserRegisterRequestDTO;
import com.trier.trier_report.dto.UserResponseDTO;
import com.trier.trier_report.entity.User;

public class UserMapper {
    public static User toEntity(UserRegisterRequestDTO payload, String encodedPassword) {
        return new User(payload.firstName(), payload.lastName(), payload.email(), encodedPassword, payload.roleId());
    }

    public static UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoleId());
    }
}
