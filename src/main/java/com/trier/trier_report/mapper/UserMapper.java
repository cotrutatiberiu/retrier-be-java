package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;
import com.trier.trier_report.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRegisterRequest command) {
        return new User(0, command.firstName(), command.lastName(), command.email(), passwordEncoder.encode(command.password()), command.role());
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(user.getId(),user.getFirstName(),user.getLastName(), user.getEmail(), user.getRole());
    }
}
