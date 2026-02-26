package com.trier.trier_report.rest;

import com.trier.trier_report.dto.OpenWeatherResponse;
import com.trier.trier_report.service.impl.WeatherServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherServiceImpl weatherServiceImpl;

    public WeatherController(WeatherServiceImpl weatherServiceImpl) {
        this.weatherServiceImpl = weatherServiceImpl;
    }

    @GetMapping("/today")
    public ResponseEntity<OpenWeatherResponse> getWeatherReport(
            @RequestParam(required = true, defaultValue = "40") String lon,
            @RequestParam(required = true, defaultValue = "40") String lat
    ) {
        OpenWeatherResponse response = weatherServiceImpl.getTodayWeatherReport(lon, lat);
        return ResponseEntity.ok(response);
    }
}
