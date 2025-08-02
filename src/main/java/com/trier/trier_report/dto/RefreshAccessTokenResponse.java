package com.trier.trier_report.dto;

public class RefreshAccessTokenResponse implements AccessToken {
    private String accessToken;

    public RefreshAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}