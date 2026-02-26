package com.trier.trier_report.rest;

import com.trier.trier_report.dto.NewsResponse;
import com.trier.trier_report.service.impl.NewsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsServiceImpl newsServiceImpl;

    public NewsController(NewsServiceImpl newsServiceImpl) {
        this.newsServiceImpl = newsServiceImpl;
    }

    @GetMapping("/today")
    public ResponseEntity<NewsResponse> getNews(
            @RequestParam(required = true, defaultValue = "") String q,
            @RequestParam(required = true, defaultValue = "") String from,
            @RequestParam(required = true, defaultValue = "") String sortBy
    ) {
        return ResponseEntity.ok(newsServiceImpl.getNews(q, from, sortBy));
    }
}
