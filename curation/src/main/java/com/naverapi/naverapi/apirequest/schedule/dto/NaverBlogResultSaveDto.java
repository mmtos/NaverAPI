package com.naverapi.naverapi.apirequest.schedule.dto;

import static com.naverapi.naverapi.apirequest.util.Md5.makeMd5;

import com.naverapi.naverapi.apirequest.domain.article.BlogArticle;
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

    private String md5HashCode;

    private String keyword;

    public BlogArticle toEntity(){
        return BlogArticle.builder()
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
        return (this.title.equals(obj.title) && this.postdate.equals(obj.postdate) && this.description.equals(obj.description) &&
                this.link.equals(obj.link) && this.bloggerlink.equals(obj.bloggerlink) && this.bloggername.equals( obj.bloggerlink ) &&
                this.keyword.equals(obj.keyword) );
    }

    @Override
    public int hashCode() {
        return (title+postdate+description+link+bloggerlink+bloggername+keyword).hashCode();
    }
}
