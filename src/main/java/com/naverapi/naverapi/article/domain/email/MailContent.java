package com.naverapi.naverapi.article.domain.email;

import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverCafeResultResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverNewsResultResponseDto;
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
    private List<NaverBlogArticleResponseDto> bList;
    private List<NaverCafeResultResponseDto> cList;
    private List<NaverNewsResultResponseDto> nList;

    @Builder
    public MailContent(User user, List<NaverBlogArticleResponseDto> bList, List<NaverCafeResultResponseDto> cList, List<NaverNewsResultResponseDto> nList) {
        this.user = user;
        this.bList = bList;
        this.cList = cList;
        this.nList = nList;
    }
}
