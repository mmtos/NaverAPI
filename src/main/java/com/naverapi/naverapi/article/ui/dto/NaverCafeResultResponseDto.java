package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateResult;
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
    public NaverCafeResultResponseDto(NaverCafeResult naverCafeResult) {
        this.title = naverCafeResult.getTitle();
        this.link = naverCafeResult.getLink();
        this.description = naverCafeResult.getDescription();
        this.cafeName = naverCafeResult.getCafeName();
        this.cafeUrl = naverCafeResult.getCafeUrl();
        this.md5HahCode = naverCafeResult.getMd5HahCode();
        this.keyword = naverCafeResult.getKeyword();
    }
}
