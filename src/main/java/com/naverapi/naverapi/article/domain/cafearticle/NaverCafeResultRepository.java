package com.naverapi.naverapi.article.domain.cafearticle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverCafeResultRepository extends JpaRepository<NaverCafeResult, Long> {
    Long countBy();
}
