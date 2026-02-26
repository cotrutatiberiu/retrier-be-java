package com.trier.trier_report.service;

import com.trier.trier_report.dto.NewsResponse;

public interface NewsService {
    NewsResponse getNews(String q, String from, String sortby);
}
