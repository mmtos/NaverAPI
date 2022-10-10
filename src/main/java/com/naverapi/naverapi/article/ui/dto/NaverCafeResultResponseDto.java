package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResult;
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

    public NaverCafeResult toEntity(){
        return NaverCafeResult.builder()
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
    public NaverCafeResultResponseDto(String title, String link, String description, String cafeName, String cafeUrl,
                                      String md5HashCode, String keyword) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.cafeName = cafeName;
        this.cafeUrl = cafeUrl;
        this.md5HahCode = md5HashCode;
        this.keyword = keyword;
    }
}
