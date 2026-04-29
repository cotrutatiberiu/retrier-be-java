package com.trier.trier_report.client.impl;

import com.trier.trier_report.client.NewsClient;
import com.trier.trier_report.dto.NewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
    public class NewsClientImpl implements NewsClient {
    @Value("${news.api.key}")
    private String API_KEY;
    private final String URL = "https://newsapi.org/v2/everything";
    private final String PARAMS = "?q={q}&from={from}&sortBy={sortBy}&apiKey={key}";
    private final RestClient restClient;

    public NewsClientImpl() {
        this.restClient = RestClient.builder().baseUrl(URL).build();
    }

    public NewsResponse fetchNews(String q, String from, String sortBy) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("q", q);
        uriVariables.put("from", from);
        uriVariables.put("sortBy", sortBy);
        uriVariables.put("key", API_KEY);

        try {
            System.out.println("Fetching");
            return this.restClient.get()
                    .uri(PARAMS, uriVariables)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new HttpClientErrorException(response.getStatusCode(), "News Client Error: " + response.getStatusText());
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new HttpClientErrorException(response.getStatusCode(), "News Client Error: " + response.getStatusText());
                    })
                    .body(NewsResponse.class);
        } catch (Exception e) {
            System.err.println("Failed to fetch news data: " + e.getMessage());
            throw new RuntimeException("News service is unavailable.", e);
        }
    }
}
