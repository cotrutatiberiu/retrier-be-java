package com.trier.trier_report.rest;

import com.trier.trier_report.config.JwtUtil;
import com.trier.trier_report.dto.*;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register") //@Valid for jakarta request body validation
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponsePayload> login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {
        User user = userService.login(request);


        String email = user.getEmail();
        LoginResponse loginResponse = new LoginResponse(jwtUtil.generateAccessToken(email), jwtUtil.generateRefreshToken(email), jwtUtil.getDefaultRefreshTokenExpirationMs());
        Cookie cookie = (loginResponse.getRefreshTokenCookie());
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponsePayload(loginResponse.getAccessToken()));
    }
}
