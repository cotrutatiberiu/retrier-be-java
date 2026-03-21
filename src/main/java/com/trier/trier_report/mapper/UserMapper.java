package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;
import com.trier.trier_report.entity.User;

public class UserMapper {
    public static User toEntity(UserRegisterRequest payload,String encodedPassword) {
        User user = new User();
        user.setFirstname(payload.firstName());
        user.setLastname(payload.lastName());
        user.setEmail(payload.email());
        user.setPassword(encodedPassword);
        user.setRoleId(payload.roleId());
        return user;
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoleId());
    }
}
