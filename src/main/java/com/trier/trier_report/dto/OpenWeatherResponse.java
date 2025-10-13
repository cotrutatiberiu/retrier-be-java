package com.trier.trier_report.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenWeatherResponse(
        Coord coord,
        List<WeatherInfo> weather,
        String base,
        MainData main,
        Integer visibility,
        WindData wind,
        RainData rain,
        CloudsData clouds,
        Long dt,
        SystemData sys,
        Integer timezone,
        Long id,
        String name,
        Integer cod
) {
}

record Coord(
        Double lon,
        Double lat
) {
}

record WeatherInfo(
        Integer id,
        String main,
        @JsonProperty("description") String weatherDescription,
        String icon
) {
}

record MainData(
        Double temp,
        @JsonProperty("feels_like") Double feelsLike,
        @JsonProperty("temp_min") Double tempMin,
        @JsonProperty("temp_max") Double tempMax,
        Integer pressure,
        Integer humidity,
        @JsonProperty("sea_level") Integer seaLevel,
        @JsonProperty("grnd_level") Integer groundLevel
) {
}

record WindData(
        Double speed,
        Integer deg,
        Double gust
) {
}

record RainData(
        @JsonProperty("1h") Double oneHour
) {
}

record CloudsData(
        Integer all
) {
}

record SystemData(
        Integer type,
        Integer id,
        String country,
        Long sunrise,
        Long sunset
) {
}