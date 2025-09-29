package com.trier.trier_report.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CustomCorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow origins (you can use "*" or restrict to specific domains)
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        // Allow specific HTTP methods\
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow specific headers
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN"));

        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);

        // Apply the configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
