package com.trier.trier_report.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record NewsResponse(
        @JsonProperty("status") String status,
        @JsonProperty("totalResults") int totalResults,
        @JsonProperty("articles") List<Article> articles
) {
    @JsonCreator
    public NewsResponse {
        if (status == null) status = "";
        if (articles == null) articles = List.of();
    }
}

record Article(
        @JsonProperty("source") Source source,
        @JsonProperty("author") String author,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("url") String url,
        @JsonProperty("urlToImage") String urlToImage,
        // Use OffsetDateTime to preserve the timezone information from ISO 8601 like "2025-10-28T13:14:14Z".
        @JsonProperty("publishedAt") OffsetDateTime publishedAt,
        @JsonProperty("content") String content
) {
    @JsonCreator
    public Article {
        // normalize nulls to empty strings if desired
        if (author == null) author = "";
        if (title == null) title = "";
        if (description == null) description = "";
        if (url == null) url = "";
        if (urlToImage == null) urlToImage = "";
        if (content == null) content = "";
        // source may be null in some payloads; keep as-is or provide fallback
        if (source == null) source = new Source(null, "");
    }
}

record Source(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {
    @JsonCreator
    public Source {
        if (id != null && id.isBlank()) id = null;
        if (name == null) name = "";
    }
}