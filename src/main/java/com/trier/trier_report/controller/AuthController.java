package com.trier.trier_report.controller;

import com.trier.trier_report.service.AuthService;
import com.trier.trier_report.util.JwtUtil;
import com.trier.trier_report.dto.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register") //@Valid for jakarta request body validation
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        UserResponseDTO response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO request, HttpServletResponse response) {
        String loggedEmail = authService.login(request);

        String accessToken = jwtUtil.generateAccessToken(loggedEmail);
        String refreshToken = jwtUtil.generateRefreshToken(loggedEmail);

        Cookie refreshTokenCookie = new Cookie("rt", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/api/auth");
        refreshTokenCookie.setMaxAge((int) (jwtUtil.getDefaultRefreshTokenExpirationSeconds()));

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new LoginResponseDTO(accessToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> resetAccessToken(@CookieValue(value = "rt", required = false) String cookieRefreshToken) {

        String newAccessToken = jwtUtil.refreshAccessToken(cookieRefreshToken);
        return ResponseEntity.ok(new RefreshAccessTokenResponseDTO(newAccessToken));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> isAuthenticated() {
        return ResponseEntity.ok(authService.isAuthenticated());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie refreshCookie = new Cookie("rt", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/api/auth");

        response.addCookie(refreshCookie);
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
    }
}
