package com.example.newsaggregator.dto;

import lombok.Data;
import java.util.List;

@Data
public class NewsApiResponse {
    private String status;
    private List<ArticleDto> articles;

    @Data
    public static class ArticleDto {
        private String title;
        private String description;
        private String url;
    }
}
