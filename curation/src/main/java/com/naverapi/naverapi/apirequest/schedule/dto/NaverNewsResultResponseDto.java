package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.article.NewsArticle;
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



    public NewsArticle toEntity(){
        return NewsArticle.builder()
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
    public NaverNewsResultResponseDto(NewsArticle newsArticle) {
        this.title = newsArticle.getTitle();
        this.link = newsArticle.getLink();
        this.description = newsArticle.getDescription();
        this.pubDate = newsArticle.getPubDate();
        this.md5HahCode = newsArticle.getMd5HahCode();
        this.keyword = newsArticle.getKeyword();
    }
}
