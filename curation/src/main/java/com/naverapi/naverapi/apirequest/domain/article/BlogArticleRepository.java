package com.naverapi.naverapi.apirequest.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
    Optional<BlogArticle> findById(Long id);

    List<BlogArticle> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<BlogArticle> findTop5ByKeywordOrderByIdDesc(String keyword);
}
