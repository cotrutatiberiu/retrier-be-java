package com.trier.trier_report.service;

import com.trier.trier_report.config.JwtUtil;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.UserLoginRequest;
import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.exception.EmailUsedException;
import com.trier.trier_report.util.LoginResult;
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
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse register(UserRegisterRequest request) {
        User user = new User(0, request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getRole());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailUsedException("Email already used");
        }

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser);
    }

    @Override
    public LoginResult authenticateAndGenerateTokens(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(), user.get().getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResult(jwtUtil.generateAccessToken(email), jwtUtil.generateRefreshToken(email), jwtUtil.getDefaultRefreshTokenExpirationMs());
    }
}
