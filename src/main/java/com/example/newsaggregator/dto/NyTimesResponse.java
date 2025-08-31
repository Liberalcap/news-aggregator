package com.example.newsaggregator.dto;

import lombok.Data;
import java.util.List;

@Data
public class NyTimesResponse {
    private List<Result> results;

    @Data
    public static class Result {
        private String title;
        private String abstractText; // maps "abstract" from JSON
        private String url;
    }
}
