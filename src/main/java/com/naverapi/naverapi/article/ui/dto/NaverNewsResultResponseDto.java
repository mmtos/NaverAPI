package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverNewsResultResponseDto {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;
    private String md5HahCode;
    private String keyword;

    public NaverNewsResult toEntity(){
        return NaverNewsResult.builder()
                .title(title)
                .originallink(originallink)
                .link(link)
                .description(description)
                .pubDate(pubDate)
                .md5HahCode(md5HahCode)
                .keyword(keyword)
                .build();
    }

    @Builder
    public NaverNewsResultResponseDto(String title, String originallink, String link, String description, String pubDate,
                                      String md5HahCode, String keyword) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.md5HahCode = md5HahCode;
        this.keyword = keyword;
    }
}
