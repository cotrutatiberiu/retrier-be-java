package com.trier.trier_report.util;

import jakarta.servlet.http.Cookie;

public class LoginDataSuccess {
    private String accessToken;
    private String refreshToken;
    private Cookie refreshTokenCookie;

    public LoginDataSuccess(String accessToken, String refreshToken, long refreshTokenExpirationMs) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        Cookie refreshTokenCookie = new Cookie("rt", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (refreshTokenExpirationMs / 100));
        this.refreshTokenCookie = refreshTokenCookie;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Cookie getRefreshTokenCookie() {
        return refreshTokenCookie;
    }

    public void setRefreshTokenCookie(Cookie refreshTokenCookie) {
        this.refreshTokenCookie = refreshTokenCookie;
    }
}
