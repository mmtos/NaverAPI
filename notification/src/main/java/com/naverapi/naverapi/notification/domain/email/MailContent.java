package com.naverapi.naverapi.notification.domain.email;

import com.naverapi.naverapi.notification.schedule.dto.BlogArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.CafeArticleDto;
import com.naverapi.naverapi.notification.schedule.dto.NewsArticleDto;
import com.naverapi.naverapi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailContent {

    User user;
    private List<BlogArticleDto> bList;
    private List<CafeArticleDto> cList;
    private List<NewsArticleDto> nList;

    @Builder
    public MailContent(User user, List<BlogArticleDto> bList, List<CafeArticleDto> cList, List<NewsArticleDto> nList) {
        this.user = user;
        this.bList = bList;
        this.cList = cList;
        this.nList = nList;
    }
}
