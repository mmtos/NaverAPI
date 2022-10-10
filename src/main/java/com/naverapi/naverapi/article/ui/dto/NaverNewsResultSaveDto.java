package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.naverapi.naverapi.article.component.util.Md5.makeMd5;

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

    public NaverNewsResult toEntity() {
        return NaverNewsResult.builder()
                .title(title)
                .originallink(originallink)
                .link(link)
                .description(description)
                .pubDate(pubDate)
                .md5HahCode(md5HahCode)
                .build();
    }

    @Builder
    public NaverNewsResultSaveDto(String title, String originallink, String link, String description, String pubDate) {
        this.title = title;
        this.originallink = originallink;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.md5HahCode = makeMd5(title+originallink+link+description+pubDate);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NaverNewsResultSaveDto)) return false;

        NaverNewsResultSaveDto obj = (NaverNewsResultSaveDto) o;

        return (this.title == obj.title && this.originallink == obj.originallink && this.link == obj.link &&
                this.description == obj.description && this.pubDate == obj.pubDate );
    }

    @Override
    public int hashCode() {
        return (title+originallink+link+description+pubDate).hashCode();
    }

}
