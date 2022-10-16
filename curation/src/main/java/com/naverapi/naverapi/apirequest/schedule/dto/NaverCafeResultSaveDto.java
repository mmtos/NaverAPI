package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.article.CafeArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.naverapi.naverapi.apirequest.util.Md5.makeMd5;

@Getter
@Setter
@NoArgsConstructor
public class NaverCafeResultSaveDto {

    private String title;

    private String link;

    private String description;

    private String cafeName;

    private String cafeUrl;

    private String md5HahCode;

    private String keyword;

    public CafeArticle toEntity(){
        return CafeArticle.builder()
                .title(title)
                .link(link)
                .description(description)
                .cafeName(cafeName)
                .cafeUrl(cafeUrl)
                .md5HahCode(md5HahCode)
                .keyword(keyword)
                .build();
    }

    @Builder
    public NaverCafeResultSaveDto(String title, String link, String description, String cafeName, String cafeUrl, String keyword) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.cafeName = cafeName;
        this.cafeUrl = cafeUrl;
        this.keyword = keyword;
        this.md5HahCode = makeMd5(title+link+description+cafeName+cafeUrl);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NaverCafeResultSaveDto)) return false;

        NaverCafeResultSaveDto obj = (NaverCafeResultSaveDto) o;

        return (this.title == obj.title && this.link == obj.link && this.description == obj.description &&
                this.cafeName == obj.cafeName && this.cafeUrl == obj.cafeUrl && this.keyword == obj.keyword );
    }

    @Override
    public int hashCode() {
        return (title+link+description+cafeName+cafeUrl+keyword).hashCode();
    }
}
