package com.trier.trier_report.util;

import com.trier.trier_report.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${auth.access-secret-token}")
    private String ACCESS_SECRET;
    @Value("${auth.refresh-secret-token}")
    private String REFRESH_SECRET;

    private final static long accessTokenExpirationMs = 15 * 60 * 1000;
    private final static long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000;

    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    }

    private Key getRefreshKey() {
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getAccessKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromAccessToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getAccessKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getEmailFromRefreshToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public long getDefaultRefreshTokenExpirationMs() {
        return System.currentTimeMillis() + refreshTokenExpirationMs;
    }

    public String refreshAccessToken(String cookieRefreshToken) {
        if (validateRefreshToken(cookieRefreshToken)) {
            return generateAccessToken(getEmailFromRefreshToken(cookieRefreshToken));
        }
        throw new RefreshTokenExpiredException("Validation failed, please login again.");
    }

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
