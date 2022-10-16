package com.naverapi.naverapi.apirequest.domain.article;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeArticleRepository extends JpaRepository<CafeArticle, Long> {
    List<CafeArticle>  findTop100ByKeywordOrderByIdDesc(String keyword);
    List<CafeArticle> findTop5ByKeywordOrderByIdDesc(String keyword);
}
