package com.trier.trier_report.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class CsrfTokenUtil {
    private final static long csrfTokenExpirationMs = 7 * 24 * 60 * 60 * 1000;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    public static long getDefaultCsrfTokenExpirationMs() {
        return System.currentTimeMillis() + csrfTokenExpirationMs;
    }

    public static String generateToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String getCsrfTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("X-CSRF-TOKEN");
    }

    public static boolean validateCsrfToken(HttpServletRequest request) {
        return getCsrfTokenFromRequest(request).equals(CookieUtil.getValueFromCookie(request, "ct"));
    }
}
