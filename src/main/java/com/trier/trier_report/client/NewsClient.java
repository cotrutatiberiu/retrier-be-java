package com.trier.trier_report.client;

import com.trier.trier_report.dto.NewsResponseDTO;

public interface NewsClient {
    NewsResponseDTO fetchNews(String q, String from, String sortBy);
}
