package com.trier.trier_report.dto;

import com.trier.trier_report.util.ResponsePayload;

public record RefreshAccessTokenResponse(
        String accessToken
) {
}