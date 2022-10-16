package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.article.BlogArticle;
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
    public NaverBlogArticleResponseDto(BlogArticle blogArticle) {
        this.title = blogArticle.getTitle();
        this.postdate = blogArticle.getPostdate();
        this.description = blogArticle.getDescription();
        this.link = blogArticle.getLink();
        this.bloggerlink = blogArticle.getBloggerlink();
        this.bloggername = blogArticle.getBloggername();
        this.md5HahCode = blogArticle.getMd5HahCode();
        this.keyword = blogArticle.getKeyword();
    }

    public BlogArticle toEntity() {
        return BlogArticle.builder()
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
