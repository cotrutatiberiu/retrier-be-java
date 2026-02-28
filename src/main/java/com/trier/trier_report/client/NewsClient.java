package com.trier.trier_report.client;

import com.trier.trier_report.dto.NewsResponse;

public interface NewsClient {
    NewsResponse fetchNews(String q, String from, String sortBy);
}
