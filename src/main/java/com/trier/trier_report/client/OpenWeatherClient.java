package com.trier.trier_report.client;


import com.trier.trier_report.dto.OpenWeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenWeatherClient {
    // TODO: move in super secret location
    private final String API_KEY = "5ad1ca86b26c60fa7e8cc5221e0a18e2c3";
    private final String URL = "https://api.openweathermap.org/data/2.5";
    private final String PARAMS = "/weather?lat={lat}&lon={lon}&appid={key}";
    private final RestClient restClient;

    public OpenWeatherClient() {
        this.restClient = RestClient.builder().baseUrl(URL).build();
    }

    public OpenWeatherResponse fetchWeather() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("lat", "40");
        uriVariables.put("lon", "40");
        uriVariables.put("key", API_KEY);

        try {
            return this.restClient.get()
                    .uri(PARAMS, uriVariables)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new HttpClientErrorException(response.getStatusCode(), "OpenWeather Client Error: " + response.getStatusText());
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new HttpClientErrorException(response.getStatusCode(), "OpenWeather Client Error: " + response.getStatusText());
                    })
                    .body(OpenWeatherResponse.class);
        } catch (Exception e) {
            System.err.println("Failed to fetch weather data: " + e.getMessage());
            throw new RuntimeException("Weather service is unavailable.", e);
        }
    }
}
