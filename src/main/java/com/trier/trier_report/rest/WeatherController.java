package com.trier.trier_report.rest;

import com.trier.trier_report.dto.OpenWeatherResponse;
import com.trier.trier_report.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/today")
    public ResponseEntity<OpenWeatherResponse> getWeatherReport(
            @RequestParam(required = true, defaultValue = "40") String lon,
            @RequestParam(required = true, defaultValue = "40") String lat
    ) {
        OpenWeatherResponse response = weatherService.getTodayWeatherReport(lon, lat);
        return ResponseEntity.ok(response);
    }
}
