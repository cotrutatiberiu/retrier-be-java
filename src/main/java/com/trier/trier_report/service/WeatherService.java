package com.trier.trier_report.service;

import com.trier.trier_report.dto.OpenWeatherResponse;

public interface WeatherService {
    OpenWeatherResponse getTodayWeatherReport(String lon, String lat);
}
