package com.trier.trier_report.client;

import com.trier.trier_report.dto.OpenWeatherResponse;

public interface WeatherClient {
    OpenWeatherResponse fetchWeather(String lon, String lat);
}
