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
    public NaverNewsResultResponseDto(NaverNewsResult naverNewsResult) {
        this.title = naverNewsResult.getTitle();
        this.link = naverNewsResult.getLink();
        this.description = naverNewsResult.getDescription();
        this.pubDate = naverNewsResult.getPubDate();
        this.md5HahCode = naverNewsResult.getMd5HahCode();
        this.keyword = naverNewsResult.getKeyword();
    }
}
