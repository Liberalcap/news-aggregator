package com.example.newsaggregator.controller;

import com.example.newsaggregator.model.Article;
import com.example.newsaggregator.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/api/news")
    public List<Article> getNews(@RequestParam(required = false) String keyword) {
        return newsService.getAggregatedNews(keyword);
    }
}
