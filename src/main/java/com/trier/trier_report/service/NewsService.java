package com.trier.trier_report.service;

import com.trier.trier_report.client.NewsClient;
import com.trier.trier_report.dto.NewsResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsClient newsClient;

    public NewsService(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    // Cache by (q, from, sortBy)
    @Cacheable(cacheNames = "news", cacheManager = "newsCacheManager", key = "#q + '|' + #from + '|' + #sortBy")
    public NewsResponse getNews(String q, String from, String sortBy) {
        return newsClient.fetchNews(q, from, sortBy);
    }
}
