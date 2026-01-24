package com.trier.trier_report.client;

import com.trier.trier_report.dto.NewsResponse;
import com.trier.trier_report.dto.OpenWeatherResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class NewsClient {
    private final String API_KEY = "1b70cc9c57074790a78b3fdf320f4e7a";
    private final String URL = "https://newsapi.org/v2/everything";
    private final String PARAMS = "?q={q}&from={from}&sortBy={sortBy}&apiKey={key}";
    private final RestClient restClient;

    public NewsClient() {
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
