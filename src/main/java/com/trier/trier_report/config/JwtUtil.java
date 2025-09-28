package com.trier.trier_report.config;

import com.trier.trier_report.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String ACCESS_SECRET = "o3bysN_g2p6L97QS2HFZurMfe7eU71trjKPo0k-d8Qo=";
    private final String REFRESH_SECRET = "5efDrTw9W54yPhqBd5Tk-5yMl9zI-Pvi5ttETa2jJNI=";

    private final long accessTokenExpirationMs = 15 * 60 * 1000;
    private final long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000;

    private final Key accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    private final Key refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromAccessToken(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getEmailFromRefreshToken(String token) {
        return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public long getDefaultRefreshTokenExpirationMs() {
        return System.currentTimeMillis() + refreshTokenExpirationMs;
    }

    public String refreshAccessToken(String refreshToken, String csrfToken) {
        if (validateRefreshToken(refreshToken) && validateAccessToken(csrfToken)) {
            return generateAccessToken(getEmailFromAccessToken(refreshToken));
        }
        throw new RefreshTokenExpiredException("Validation failed, please login again.");
    }
}
