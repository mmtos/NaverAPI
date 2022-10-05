package com.naverapi.naverapi.interfaces.dtos;

import com.naverapi.naverapi.domain.NaverBlogResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverBlogResultSaveDto {
    private String titile;
    private String postdate;
    private String description;
    private String link;
    private String bloggerlink;
    private String bloggername;

    public NaverBlogResult toEntity(){
        return NaverBlogResult.builder()
                .titile(titile)
                .postdate(postdate)
                .description(description)
                .link(link)
                .bloggerlink(bloggerlink)
                .bloggername(bloggername)
                .build();
    }

    @Builder
    public NaverBlogResultSaveDto(String titile, String postdate, String description, String link, String bloggerlink, String bloggername) {
        this.titile = titile;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
    }
}
