package com.naverapi.naverapi.article.domain.blogarticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NaverBlogDateRepository extends JpaRepository<NaverBlogDateResult, Long> {
    Optional<NaverBlogDateResult> findById(Long id);

    List<NaverBlogDateResult> findTop100ByKeywordOrderByIdDesc(String keyword);

    List<NaverBlogDateResult> findTop5ByKeywordOrderByIdDesc(String keyword);
}
