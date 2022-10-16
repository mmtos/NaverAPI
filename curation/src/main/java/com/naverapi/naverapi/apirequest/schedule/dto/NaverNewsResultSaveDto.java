package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.article.NewsArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.naverapi.naverapi.apirequest.util.Md5.makeMd5;

@Getter
@Setter
@NoArgsConstructor
public class NaverNewsResultSaveDto {
    private String title;

    private String originallink;

    private String link;

    private String description;

    private String pubDate;

    private String md5HahCode;

    private String keyword;

    public NewsArticle toEntity() {
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
    public NaverNewsResultSaveDto(String title, String originallink, String link, String description,
                                  String pubDate, String keyword) {
        this.title = title;
        this.originallink = originallink;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.keyword = keyword;
        this.md5HahCode = makeMd5(title+originallink+link+description+pubDate);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NaverNewsResultSaveDto)) return false;

        NaverNewsResultSaveDto obj = (NaverNewsResultSaveDto) o;

        return (this.title == obj.title && this.originallink == obj.originallink && this.link == obj.link &&
                this.description == obj.description && this.pubDate == obj.pubDate && this.keyword == obj.keyword );
    }

    @Override
    public int hashCode() {
        return (title+originallink+link+description+pubDate+keyword).hashCode();
    }

}
