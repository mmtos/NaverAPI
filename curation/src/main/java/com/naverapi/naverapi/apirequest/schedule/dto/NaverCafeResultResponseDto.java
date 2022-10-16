package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.article.CafeArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverCafeResultResponseDto {
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
    public NaverCafeResultResponseDto(CafeArticle cafeArticle) {
        this.title = cafeArticle.getTitle();
        this.link = cafeArticle.getLink();
        this.description = cafeArticle.getDescription();
        this.cafeName = cafeArticle.getCafeName();
        this.cafeUrl = cafeArticle.getCafeUrl();
        this.md5HahCode = cafeArticle.getMd5HahCode();
        this.keyword = cafeArticle.getKeyword();
    }
}
