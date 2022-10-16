package com.naverapi.naverapi.notification.application;

import com.naverapi.naverapi.notification.schedule.dto.BlogArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.CafeArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.NewsArticleDto;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class ArticleRequestService {
    // TODO: curation 모듈로 부터 Article Data 가져오기
    public List<BlogArticleDto> getBlogArticleListForSendEmail(String keyword ) {
        return Collections.emptyList();
    }

    public List<CafeArticleDto> getCafeArticleListForSendEmail(String keyword ) {
        return Collections.emptyList();
    }

    public List<NewsArticleDto> getNewsArticleListForSendEmail(String keyword ) {
        return Collections.emptyList();
    }
}
