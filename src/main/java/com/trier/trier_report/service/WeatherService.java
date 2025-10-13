package com.trier.trier_report.service;

import com.trier.trier_report.client.OpenWeatherClient;
import com.trier.trier_report.dto.OpenWeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final OpenWeatherClient openWeatherClient;

    public WeatherService(OpenWeatherClient openWeatherClient){
        this.openWeatherClient=openWeatherClient;
    }

    public OpenWeatherResponse getTodayWeatherReport(){
        return openWeatherClient.fetchWeather();
    }
}
