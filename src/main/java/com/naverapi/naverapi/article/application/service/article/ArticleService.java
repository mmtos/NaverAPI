package com.naverapi.naverapi.article.application.service.article;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateRepository;
import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResultRepository;
import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResultRepository;
import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverCafeResultResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverNewsResultResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final NaverBlogDateRepository blogRepo;

    private final NaverCafeResultRepository cafeRepo;

    private final NaverNewsResultRepository newsRepo;

    @Transactional(readOnly = true)
    public List<NaverBlogArticleResponseDto> getBlogArticleListForSendEmail( String keyword ) {
        return blogRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverBlogArticleResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverBlogArticleResponseDto> getBlogArticleBeforeSaving100( String keyword ) {
        return blogRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverBlogArticleResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverCafeResultResponseDto> getCafeArticleListForSendEmail(String keyword ) {
        return cafeRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverCafeResultResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverCafeResultResponseDto> getCafeArticleBeforeSaving100( String keyword ) {
        return cafeRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverCafeResultResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverNewsResultResponseDto> getNewsArticleListForSendEmail(String keyword ) {
        return newsRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverNewsResultResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverNewsResultResponseDto> getNewsArticleBeforeSaving100( String keyword ) {
        return newsRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverNewsResultResponseDto::new).collect(Collectors.toList());
    }


}
