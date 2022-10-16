package com.naverapi.naverapi.notification.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogArticleDto {

    private String title;

    private String postdate;

    private String description;

    private String link;

    private String bloggerlink;

    private String bloggername;

    private String md5HahCode;

    private String keyword;

    @Override
    public boolean equals(Object o) {
        if( !(o instanceof BlogArticleDto)) return false;

        BlogArticleDto obj = (BlogArticleDto) o;
        return ( this.title.equals(obj.title) && this.postdate.equals(obj.postdate) && this.description.equals(description) &&
                this.link.equals(obj.link) && this.bloggerlink.equals(obj.bloggerlink) && this.bloggername.equals(obj.bloggername)
                && this.md5HahCode.equals(obj.md5HahCode) && this.keyword.equals(keyword));
    }

    @Override
    public int hashCode() {
        return (title+postdate+description+link+bloggerlink+bloggername+md5HahCode+keyword).hashCode();
    }
}
