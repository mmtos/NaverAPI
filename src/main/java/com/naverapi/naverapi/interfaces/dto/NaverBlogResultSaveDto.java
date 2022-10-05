package com.naverapi.naverapi.interfaces.dto;

import com.naverapi.naverapi.domain.NaverBlogResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverBlogResultSaveDto {
    private String title;
    private String postdate;
    private String description;
    private String link;
    private String bloggerlink;
    private String bloggername;

    public NaverBlogResult toEntity(){
        return NaverBlogResult.builder()
                .title(title)
                .postdate(postdate)
                .description(description)
                .link(link)
                .bloggerlink(bloggerlink)
                .bloggername(bloggername)
                .build();
    }

    @Builder
    public NaverBlogResultSaveDto(String title, String postdate, String description, String link, String bloggerlink, String bloggername) {
        this.title = title;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
    }
}
