package com.naverapi.naverapi.article.application.service.article;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogResult;
import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogResultRepository;
import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResult;
import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResultRepository;
import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResult;
import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResultRepository;
import com.naverapi.naverapi.article.ui.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final NaverBlogResultRepository blogRepo;

    private final NaverCafeResultRepository cafeRepo;

    private final NaverNewsResultRepository newsRepo;

    public List<NaverBlogResultSaveDto> deleteDuplicationBlogArticle( String keyword , List<NaverBlogResultSaveDto> newList ){

        List<NaverBlogResultSaveDto> list = new ArrayList<>();
        List<NaverBlogArticleResponseDto> oldList = getBlogArticleBeforeSaving100(keyword);

        List<NaverBlogResult> newEntityList = new ArrayList<>();
        List<NaverBlogResult> oldEntityList = new ArrayList<>();

        for ( NaverBlogResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverBlogArticleResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<NaverBlogResult> newEntitySet = new HashSet<>(newEntityList);
        Set<NaverBlogResult> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<NaverBlogResult> resultList = new ArrayList<>(newEntitySet);

        for (NaverBlogResult nbr : resultList) {
            list.add( NaverBlogResultSaveDto.builder()
                            .title(nbr.getTitle())
                            .postdate(nbr.getPostdate())
                            .description(nbr.getDescription())
                            .link(nbr.getLink())
                            .bloggerlink(nbr.getBloggerlink())
                            .bloggername(nbr.getBloggername())
                            .keyword(nbr.getKeyword())
                            .build() );
        }

        return list;
    }

    public List<NaverCafeResultSaveDto> deleteDuplicationCafeArticle(String keyword , List<NaverCafeResultSaveDto> newList ){

        List<NaverCafeResultSaveDto> list = new ArrayList<>();
        List<NaverCafeResultResponseDto> oldList = getCafeArticleBeforeSaving100(keyword);

        List<NaverCafeResult> newEntityList = new ArrayList<>();
        List<NaverCafeResult> oldEntityList = new ArrayList<>();

        for ( NaverCafeResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverCafeResultResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<NaverCafeResult> newEntitySet = new HashSet<>(newEntityList);
        Set<NaverCafeResult> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<NaverCafeResult> resultList = new ArrayList<>(newEntitySet);

        for (NaverCafeResult ncr : resultList) {
            list.add( NaverCafeResultSaveDto.builder()
                            .title(ncr.getTitle())
                            .link(ncr.getLink())
                            .description(ncr.getDescription())
                            .cafeName(ncr.getCafeName())
                            .cafeUrl(ncr.getCafeUrl())
                            .keyword(ncr.getKeyword())
                            .build() );
        }

        return list;
    }

    public List<NaverNewsResultSaveDto> deleteDuplicationNewsArticle( String keyword , List<NaverNewsResultSaveDto> newList ){

        List<NaverNewsResultSaveDto> list = new ArrayList<>();
        List<NaverNewsResultResponseDto> oldList = getNewsArticleBeforeSaving100(keyword);

        List<NaverNewsResult> newEntityList = new ArrayList<>();
        List<NaverNewsResult> oldEntityList = new ArrayList<>();

        for ( NaverNewsResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverNewsResultResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<NaverNewsResult> newEntitySet = new HashSet<>(newEntityList);
        Set<NaverNewsResult> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<NaverNewsResult> resultList = new ArrayList<>(newEntitySet);

        for (NaverNewsResult nnr : resultList) {
            list.add( NaverNewsResultSaveDto.builder()
                    .title(nnr.getTitle())
                    .originallink(nnr.getOriginallink())
                    .link(nnr.getLink())
                    .description(nnr.getDescription())
                    .pubDate(nnr.getPubDate())
                    .keyword(nnr.getKeyword())
                    .build() );
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<NaverBlogArticleResponseDto> getBlogArticleListForSendEmail( String keyword ) {
        return blogRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverBlogArticleResponseDto::new).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "blogArticleList")
    @Transactional(readOnly = true)
    private List<NaverBlogArticleResponseDto> getBlogArticleBeforeSaving100( String keyword ) {
        return blogRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverBlogArticleResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverCafeResultResponseDto> getCafeArticleListForSendEmail(String keyword ) {
        return cafeRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverCafeResultResponseDto::new).collect(Collectors.toList());
    }
    @Cacheable(cacheNames = "cafeArticleList")
    @Transactional(readOnly = true)
    private List<NaverCafeResultResponseDto> getCafeArticleBeforeSaving100( String keyword ) {
        return cafeRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverCafeResultResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NaverNewsResultResponseDto> getNewsArticleListForSendEmail(String keyword ) {
        return newsRepo.findTop5ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverNewsResultResponseDto::new).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "newsArticleList")
    @Transactional(readOnly = true)
    private List<NaverNewsResultResponseDto> getNewsArticleBeforeSaving100( String keyword ) {
        return newsRepo.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverNewsResultResponseDto::new).collect(Collectors.toList());
    }


}
