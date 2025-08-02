package com.trier.trier_report.dto;

public class LoginResponsePayload {
    private String accessToken;

    public LoginResponsePayload(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
