package com.trier.trier_report.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String ACCESS_SECRET = "replace-this-with-a-secure-secret-key-which-is-long-enough1";
    private final String REFRESH_SECRET = "replace-this-with-a-secure-secret-key-which-is-long-enough2";

    private final long accessTokenExpirationMs = 15 * 60 * 1000;
    private final long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000;

    private final Key accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    private final Key refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJwt(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJwt(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromAccessToken(String token){
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJwt(token).getBody().getSubject();
    }

    public long getDefaultRefreshTokenExpirationMs(){
        return System.currentTimeMillis() + refreshTokenExpirationMs;
    }
}
