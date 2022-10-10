package com.naverapi.naverapi.article.domain.blogarticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaverBlogDateRepository extends JpaRepository<NaverBlogDateResult, Long> {
    Optional<NaverBlogDateResult> findById(Long id);
    Long countBy();
}
