package com.trier.trier_report.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class CsrfController {

    @GetMapping("/csrf")
    public ResponseEntity<Void> csrf(CsrfToken token) {
        // Touching `token` ensures it is generated and the cookie repository can write it
        return ResponseEntity.noContent().build(); // 204, no payload
    }
}
