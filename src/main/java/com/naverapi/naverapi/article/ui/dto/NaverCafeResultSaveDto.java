package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

import static com.naverapi.naverapi.article.component.util.Md5.makeMd5;

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

    public NaverCafeResult toEntity(){
        return NaverCafeResult.builder()
                .title(title)
                .link(link)
                .description(description)
                .cafeName(cafeName)
                .cafeUrl(cafeUrl)
                .md5HahCode(md5HahCode)
                .build();
    }

    @Builder
    public NaverCafeResultSaveDto(String title, String link, String description, String cafeName, String cafeUrl) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.cafeName = cafeName;
        this.cafeUrl = cafeUrl;
        this.md5HahCode = makeMd5(title+link+description+cafeName+cafeUrl);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NaverCafeResultSaveDto)) return false;

        NaverCafeResultSaveDto obj = (NaverCafeResultSaveDto) o;

        return (this.title == obj.title && this.link == obj.link && this.description == obj.description &&
                this.cafeName == obj.cafeName && this.cafeUrl == obj.cafeUrl );
    }

    @Override
    public int hashCode() {
        return (title+link+description+cafeName+cafeUrl).hashCode();
    }
}
