package com.naverapi.naverapi.apirequest.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<NewsArticle> findTop5ByKeywordOrderByIdDesc(String keyword);
}
