package com.example.newsaggregator.dto;

import lombok.Data;
import java.util.List;

@Data
public class NyTimesArticleSearchResponse {

    private Response response;

    @Data
    public static class Response {
        private List<Doc> docs;
    }

    @Data
    public static class Doc {
        private String web_url;
        private Headline headline;
        private String snippet;
    }

    @Data
    public static class Headline {
        private String main;
    }
}
