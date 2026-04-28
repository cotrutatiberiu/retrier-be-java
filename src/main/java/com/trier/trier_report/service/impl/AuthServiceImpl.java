package com.trier.trier_report.service.impl;

import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.UserLoginRequestDTO;
import com.trier.trier_report.dto.UserRegisterRequestDTO;
import com.trier.trier_report.dto.UserResponseDTO;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.exception.EmailUsedException;
import com.trier.trier_report.mapper.UserMapper;
import com.trier.trier_report.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRegisterRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = UserMapper.toEntity(request, encodedPassword);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailUsedException("Email already used");
        }

        User savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    public String login(UserLoginRequestDTO userLoginRequestDTO) {
        String email = userLoginRequestDTO.email();
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if (user.isEmpty() || !passwordEncoder.matches(userLoginRequestDTO.password(), user.get().getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.email(), userLoginRequestDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return email;
    }

    public String isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName();
    }
}
