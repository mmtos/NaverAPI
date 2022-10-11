package com.naverapi.naverapi.article.domain.newsarticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NaverNewsResultRepository extends JpaRepository<NaverNewsResult, Long> {
    List<NaverNewsResult> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<NaverNewsResult> findTop5ByKeywordOrderByIdDesc(String keyword);
}
