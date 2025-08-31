package com.example.newsaggregator.service;

import com.example.newsaggregator.dto.NewsApiResponse;
import com.example.newsaggregator.dto.NyTimesArticleSearchResponse;
import com.example.newsaggregator.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    @Value("${newsapi.url}")
    private String newsApiUrl;
    @Value("${newsapi.key}")
    private String newsApiKey;

    @Value("${nytimes.url}")
    private String nytimesUrl;
    @Value("${nytimes.key}")
    private String nytimesKey;

    private final WebClient webClient = WebClient.create();

    // Main method to fetch and merge articles
    public List<Article> getAggregatedNews(String keyword) {
        List<Article> articles = new ArrayList<>();
        articles.addAll(fetchNewsApiArticles(keyword));
        articles.addAll(fetchNyTimesArticles(keyword));
        return articles;
    }

    // Fetch NewsAPI articles
    private List<Article> fetchNewsApiArticles(String keyword) {
        try {
            String url = newsApiUrl + "&apiKey=" + newsApiKey;
            if (keyword != null && !keyword.isEmpty()) {
                url += "&q=" + keyword;
            }

            Mono<NewsApiResponse> responseMono = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(NewsApiResponse.class);

            NewsApiResponse response = responseMono.block();
            List<Article> result = new ArrayList<>();
            if (response != null && response.getArticles() != null) {
                response.getArticles().forEach(a ->
                        result.add(new Article(a.getTitle(), a.getDescription(), a.getUrl(), "NewsAPI"))
                );
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Fetch NYTimes articles
    private List<Article> fetchNyTimesArticles(String keyword) {
        try {
            String url = nytimesUrl + "?api-key=" + nytimesKey;
            if (keyword != null && !keyword.isEmpty()) {
                url += "&q=" + keyword;
            }

            Mono<NyTimesArticleSearchResponse> responseMono = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(NyTimesArticleSearchResponse.class);

            NyTimesArticleSearchResponse response = responseMono.block();
            List<Article> result = new ArrayList<>();
            if (response != null && response.getResponse() != null && response.getResponse().getDocs() != null) {
                response.getResponse().getDocs().forEach(doc ->
                        result.add(new Article(
                                doc.getHeadline().getMain(),
                                doc.getSnippet(),
                                doc.getWeb_url(),
                                "NYTimes"
                        ))
                );
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
