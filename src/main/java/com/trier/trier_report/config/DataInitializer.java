package com.trier.trier_report.config;

import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository;

    public DataInitializer(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String testEmail = "test@email.com";
        if (!userRepository.existsByEmail(testEmail)) {
            userService.register(new UserRegisterRequest("testFirstname", "testLastname", testEmail, "testPassword1234", User.Role.USER));
        }
    }
}
