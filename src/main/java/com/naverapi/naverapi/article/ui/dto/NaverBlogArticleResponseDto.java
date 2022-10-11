package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogResult;
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

    @Builder
    public NaverBlogArticleResponseDto(NaverBlogResult naverBlogResult) {
        this.title = naverBlogResult.getTitle();
        this.postdate = naverBlogResult.getPostdate();
        this.description = naverBlogResult.getDescription();
        this.link = naverBlogResult.getLink();
        this.bloggerlink = naverBlogResult.getBloggerlink();
        this.bloggername = naverBlogResult.getBloggername();
        this.md5HahCode = naverBlogResult.getMd5HahCode();
        this.keyword = naverBlogResult.getKeyword();
    }

    public NaverBlogResult toEntity() {
        return NaverBlogResult.builder()
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

    @Override
    public boolean equals(Object o) {
        if( !(o instanceof NaverBlogArticleResponseDto)) return false;

        NaverBlogArticleResponseDto obj = (NaverBlogArticleResponseDto) o;
        return ( this.title.equals(obj.title) && this.postdate.equals(obj.postdate) && this.description.equals(description) &&
                this.link.equals(obj.link) && this.bloggerlink.equals(obj.bloggerlink) && this.bloggername.equals(obj.bloggername)
                && this.md5HahCode.equals(obj.md5HahCode) && this.keyword.equals(keyword));
    }


    @Override
    public int hashCode() {
        return (title+postdate+description+link+bloggerlink+bloggername+md5HahCode+keyword).hashCode();
    }
}
