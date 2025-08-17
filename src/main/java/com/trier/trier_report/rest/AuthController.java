package com.trier.trier_report.rest;

import com.trier.trier_report.config.JwtUtil;
import com.trier.trier_report.dto.*;
import com.trier.trier_report.service.UserService;
import com.trier.trier_report.util.LoginResult;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {
        LoginResult loginResult = userService.authenticateAndGenerateTokens(request);

        Cookie refreshTokenCookie = new Cookie("rt", loginResult.getAccessToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (jwtUtil.getDefaultRefreshTokenExpirationMs() / 100));

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new LoginResponse(loginResult.getAccessToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> resetAccessToken(@CookieValue(value = "rt", required = false) String refreshToken, @RequestHeader(value = "X-CSRF-TOKEN", required = false) String csrfToken) {

        String newAccessToken = jwtUtil.refreshAccessToken(refreshToken, csrfToken);
        return ResponseEntity.ok(new RefreshAccessTokenResponse(newAccessToken));
    }
}
