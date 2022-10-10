package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverBlogArticleResponseDto {

    private String title;

    private String postdate;

    private String description;

    private String link;

    private String bloggerlink;

    private String bloggername;

    private String md5HahCode;

    private String keyword;

    public NaverBlogDateResult toEntity() {
        return NaverBlogDateResult.builder()
                .title(title)
                .postdate(postdate)
                .description(description)
                .link(link)
                .bloggerlink(bloggerlink)
                .bloggername(bloggername)
                .md5HahCode(md5HahCode)
                .keyword(keyword)
                .build();
    }

    @Builder
    public NaverBlogArticleResponseDto( String title, String postdate, String description, String link,
                                        String bloggerlink, String bloggername, String md5HahCode, String keyword) {
        this.title = title;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
        this.md5HahCode = md5HahCode;
        this.keyword = keyword;
    }
}
