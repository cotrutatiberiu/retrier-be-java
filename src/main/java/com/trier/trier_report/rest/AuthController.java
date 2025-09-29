package com.trier.trier_report.rest;

import com.trier.trier_report.util.CsrfTokenUtil;
import com.trier.trier_report.util.JwtUtil;
import com.trier.trier_report.dto.*;
import com.trier.trier_report.service.UserService;

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

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") //@Valid for jakarta request body validation
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletResponse response) {
        String loggedEmail = userService.login(request);

        String accessToken = JwtUtil.generateAccessToken(loggedEmail);
        String refreshToken = JwtUtil.generateRefreshToken(loggedEmail);
        String csrfToken = CsrfTokenUtil.generateToken();

        Cookie refreshTokenCookie = new Cookie("rt", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (JwtUtil.getDefaultRefreshTokenExpirationMs() / 100));

        Cookie csrfTokenCookie = new Cookie("ct", csrfToken);
        csrfTokenCookie.setHttpOnly(true);
        csrfTokenCookie.setPath("/");
        csrfTokenCookie.setMaxAge((int) (CsrfTokenUtil.getDefaultCsrfTokenExpirationMs() / 100));

        response.addCookie(refreshTokenCookie);
        response.addCookie(csrfTokenCookie);

        return ResponseEntity.ok(new LoginResponse(accessToken, csrfToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> resetAccessToken(@CookieValue(value = "rt", required = false) String cookieRefreshToken, @CookieValue(value = "ct", required = false) String cookieCsrfToken, @RequestHeader(value = "X-CSRF-TOKEN", required = false) String headerCsrfToken) {

        String newAccessToken = JwtUtil.refreshAccessToken(cookieRefreshToken);
        return ResponseEntity.ok(new RefreshAccessTokenResponse(newAccessToken));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> isAuthenticated() {
        return ResponseEntity.ok(userService.isAuthenticated());
    }
}
