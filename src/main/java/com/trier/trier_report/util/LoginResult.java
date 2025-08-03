package com.trier.trier_report.util;

import jakarta.servlet.http.Cookie;

public class LoginResult {
    private String accessToken;
    private String refreshToken;

    public LoginResult(String accessToken, String refreshToken, long refreshTokenExpirationMs) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
}
