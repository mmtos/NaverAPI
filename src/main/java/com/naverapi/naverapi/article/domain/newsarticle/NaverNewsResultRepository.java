package com.naverapi.naverapi.article.domain.newsarticle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverNewsResultRepository extends JpaRepository<NaverNewsResult, Long> {
    Long countBy();
}
