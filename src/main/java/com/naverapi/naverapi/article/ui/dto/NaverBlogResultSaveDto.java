package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogDateResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.naverapi.naverapi.article.component.util.Md5.makeMd5;

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

    private String md5HashCode;

    private String keyword;

    public NaverBlogDateResult toEntity(){
        return NaverBlogDateResult.builder()
                .title(title)
                .postdate(postdate)
                .description(description)
                .link(link)
                .bloggerlink(bloggerlink)
                .bloggername(bloggername)
                .md5HahCode(md5HashCode)
                .keyword(keyword)
                .build();
    }

    @Builder
    public NaverBlogResultSaveDto(String title, String postdate, String description,
                                  String link, String bloggerlink, String bloggername, String keyword) {
        this.title = title;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
        this.keyword = keyword;
        this.md5HashCode = makeMd5(title+postdate+description+link+bloggerlink+bloggername );
    }

    @Override
    public boolean equals(Object o) {
        if( !(o instanceof NaverBlogResultSaveDto) ) return false;

        NaverBlogResultSaveDto obj = (NaverBlogResultSaveDto) o;
        return (this.title == obj.title && this.postdate == obj.postdate && this.description == obj.description &&
                this.link == obj.link && this.bloggerlink == obj.bloggerlink && this.bloggername == obj.bloggerlink &&
                this.keyword == obj.keyword );
    }

    @Override
    public int hashCode() {
        return (title+postdate+description+link+bloggerlink+bloggername+keyword).hashCode();
    }
}
