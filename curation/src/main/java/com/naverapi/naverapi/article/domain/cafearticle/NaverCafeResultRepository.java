package com.naverapi.naverapi.article.domain.cafearticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NaverCafeResultRepository extends JpaRepository<NaverCafeResult, Long> {
    List<NaverCafeResult>  findTop100ByKeywordOrderByIdDesc(String keyword);
    List<NaverCafeResult> findTop5ByKeywordOrderByIdDesc(String keyword);
}
