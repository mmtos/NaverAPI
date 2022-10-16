package com.naverapi.naverapi.apirequest.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeArticleRepository extends JpaRepository<CafeArticle, Long> {
    List<CafeArticle>  findTop100ByKeywordOrderByIdDesc(String keyword);
    List<CafeArticle> findTop5ByKeywordOrderByIdDesc(String keyword);
}
