package com.naverapi.naverapi.article.domain.blogarticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NaverBlogResultRepository extends JpaRepository<NaverBlogResult, Long> {
    Optional<NaverBlogResult> findById(Long id);

    List<NaverBlogResult> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<NaverBlogResult> findTop5ByKeywordOrderByIdDesc(String keyword);
}
