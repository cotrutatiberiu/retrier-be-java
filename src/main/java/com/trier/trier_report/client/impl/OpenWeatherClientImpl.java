package com.trier.trier_report.client.impl;


import com.trier.trier_report.client.WeatherClient;
import com.trier.trier_report.dto.OpenWeatherResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenWeatherClientImpl implements WeatherClient {
    @Value("${weather.api.key}")
    private String API_KEY;
    private final String URL = "https://api.openweathermap.org/data/2.5";
    private final String PARAMS = "/weather?lat={lat}&lon={lon}&appid={key}";
    private final RestClient restClient;

    public OpenWeatherClientImpl() {
        this.restClient = RestClient.builder().baseUrl(URL).build();
    }

    public OpenWeatherResponse fetchWeather(String lon, String lat) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("lat", lon);
        uriVariables.put("lon", lat);
        uriVariables.put("key", API_KEY);
        System.out.println("Open Weather third party hit");
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
