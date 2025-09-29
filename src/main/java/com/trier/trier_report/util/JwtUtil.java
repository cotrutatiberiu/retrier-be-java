package com.trier.trier_report.util;

import com.trier.trier_report.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private final static String ACCESS_SECRET = "xYp-R_8oJ9yA4bL1cT-sZ2qFh7eG3dM0kNvU6wQxP5iIuO_zVlLrKjHmYgXfEwCbDa-sYp-R_8oJ9yA4bL1cT-sZ2qFh7eG3dM0k";
    private final static String REFRESH_SECRET = "B7tFh2gVjK3rL9cM4pXzS0qW8yE6oIuA5dC1vBnQxYZaT_gHjDkFmLoPqRsTuVwXyZ0aBcDeFgHiJkLmNoPqRsTuVwXyZ0aBcDeF";

    private final static long accessTokenExpirationMs = 15 * 60 * 1000;
    private final static long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000;

    private final static Key accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    private final static Key refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());

    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static String getEmailFromAccessToken(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public static String getEmailFromRefreshToken(String token) {
        return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public static long getDefaultRefreshTokenExpirationMs() {
        return System.currentTimeMillis() + refreshTokenExpirationMs;
    }

    public static String refreshAccessToken(String cookieRefreshToken) {
        if (validateRefreshToken(cookieRefreshToken)) {
            return generateAccessToken(getEmailFromRefreshToken(cookieRefreshToken));
        }
        throw new RefreshTokenExpiredException("Validation failed, please login again.");
    }

    public static String getAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
