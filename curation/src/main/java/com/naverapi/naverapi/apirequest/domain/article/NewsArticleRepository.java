package com.naverapi.naverapi.apirequest.domain.article;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<NewsArticle> findTop5ByKeywordOrderByIdDesc(String keyword);
}
