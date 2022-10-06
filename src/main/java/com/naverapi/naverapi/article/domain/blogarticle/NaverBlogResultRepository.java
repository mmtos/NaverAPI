package com.naverapi.naverapi.article.domain.blogarticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NaverBlogResultRepository extends JpaRepository<NaverBlogResult, Long> {
    Long countBy();
}
