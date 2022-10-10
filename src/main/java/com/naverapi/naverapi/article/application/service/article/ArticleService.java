package com.naverapi.naverapi.article.application.service.article;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateRepository;
import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateResult;
import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final NaverBlogDateRepository naverBlogDateRepository;

    @Transactional(readOnly = true)
    public List<NaverBlogArticleResponseDto> getRowBlogArticle(int rowLine) {

        List<NaverBlogArticleResponseDto> list = new ArrayList<>();
        Long start = getTotalBlogArticleRow();
        Long end = ( (start-rowLine) > 0 ) ? (start-rowLine+1) : 1L;

        if(rowLine == 0 || start == 0) return null;

        for (Long i = start; i >= end; i--) {
            Optional<NaverBlogDateResult> naverBlogDateResult = naverBlogDateRepository.findById(i);
            if(naverBlogDateResult.isPresent()) {
                NaverBlogDateResult article = naverBlogDateResult.get();
                list.add(  NaverBlogArticleResponseDto.builder()
                                .title(article.getTitle())
                                .postdate(article.getPostdate())
                                .description(article.getDescription())
                                .link(article.getLink())
                                .bloggerlink(article.getBloggerlink())
                                .bloggername(article.getBloggername())
                                .md5HahCode(article.getMd5HahCode())
                                .keyword(article.getKeyword())
                                .build());
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    private Long getTotalBlogArticleRow() {
        return naverBlogDateRepository.countBy();
    }

}
