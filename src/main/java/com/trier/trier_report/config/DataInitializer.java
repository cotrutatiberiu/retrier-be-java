package com.trier.trier_report.config;

import com.trier.trier_report.dao.CurrencyRepository;
import com.trier.trier_report.dao.RoleRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.entity.Currency;
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
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, CurrencyRepository currencyRepository, PasswordEncoder encoder) {
        return args -> {
            Optional<Role> userRole = roleRepository.findByName("USER");
            Role role = userRole.orElse(null);
            Optional<User> u = userRepository.findByEmail("test@email.com");
            if (userRole.isPresent() && u.isEmpty()) {
                User user = new User("testFirstname", "testLastname", "test@email.com", encoder.encode("testPassword1234"), role.getId());

                userRepository.save(user);
                System.out.println("User initialized.");
            }
        };
    }
}
