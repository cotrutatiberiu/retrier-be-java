package com.trier.trier_report.config;

import com.trier.trier_report.dao.RoleRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.entity.Role;
import com.trier.trier_report.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            Optional<Role> userRole = roleRepository.findByName("USER");
            Role role = userRole.orElse(null);
            if (userRole.isPresent()) {
                User user = new User();
                user.setFirstname("testFirstname");
                user.setLastname("testLastname");
                user.setEmail("test@email.com");
                user.setPassword(encoder.encode("testPassword1234"));
                user.setRoleId(role.getId());

                userRepository.save(user);
                System.out.println("User initialized.");
            }
        };
    }
}
