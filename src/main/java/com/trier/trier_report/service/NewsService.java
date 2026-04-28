package com.trier.trier_report.service;

import com.trier.trier_report.dto.NewsResponseDTO;

public interface NewsService {
    NewsResponseDTO getNews(String q, String from, String sortby);
}
