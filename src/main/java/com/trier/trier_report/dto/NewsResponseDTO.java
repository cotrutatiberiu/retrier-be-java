package com.trier.trier_report.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record NewsResponseDTO(
        @JsonProperty("status") String status,
        @JsonProperty("totalResults") int totalResults,
        @JsonProperty("articles") List<ArticleDTO> articleDTOS
) {
    @JsonCreator
    public NewsResponseDTO {
        if (status == null) status = "";
        if (articleDTOS == null) articleDTOS = List.of();
    }
}

record ArticleDTO(
        @JsonProperty("source") SourceDTO sourceDTO,
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
    public ArticleDTO {
        // normalize nulls to empty strings if desired
        if (author == null) author = "";
        if (title == null) title = "";
        if (description == null) description = "";
        if (url == null) url = "";
        if (urlToImage == null) urlToImage = "";
        if (content == null) content = "";
        // source may be null in some payloads; keep as-is or provide fallback
        if (sourceDTO == null) sourceDTO = new SourceDTO(null, "");
    }
}

record SourceDTO(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {
    @JsonCreator
    public SourceDTO {
        if (id != null && id.isBlank()) id = null;
        if (name == null) name = "";
    }
}