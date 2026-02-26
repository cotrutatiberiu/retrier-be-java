package com.trier.trier_report.service.impl;

import com.trier.trier_report.client.OpenWeatherClient;
import com.trier.trier_report.dto.OpenWeatherResponse;
import com.trier.trier_report.service.WeatherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherClient openWeatherClient;

    public WeatherServiceImpl(OpenWeatherClient openWeatherClient) {
        this.openWeatherClient = openWeatherClient;
    }

    @Cacheable(value = "lon", key = "#lon + ':' + #lat")
    public OpenWeatherResponse getTodayWeatherReport(String lon, String lat) {
        return openWeatherClient.fetchWeather(lon, lat);
    }
}
