package com.naverapi.naverapi.apirequest.domain.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "BLOG_ARTICLE")
@NoArgsConstructor
public class BlogArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String title;

    @Column(length = 1024, nullable = false)
    private String postdate;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(length = 1024, nullable = false)
    private String link;

    @Column(length = 1024, nullable = false)
    private String bloggerlink;

    @Column(length = 1024, nullable = false)
    private String bloggername;

    @Column(length = 1024, nullable = false)
    private String md5HahCode;

    @Column(length = 256, nullable = false)
    private String keyword;

    @Builder
    public BlogArticle(String title, String postdate, String description, String link,
                       String bloggerlink, String bloggername, String md5HahCode, String keyword){
        this.title = title;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
        this.md5HahCode = md5HahCode;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BlogArticle)) return false;

        BlogArticle obj = (BlogArticle) o;
        return (this.title.equals(obj.title) && this.postdate.equals(obj.postdate) && this.description.equals(obj.description) &&
                this.link.equals(obj.link) && this.bloggerlink.equals(obj.bloggerlink) && this.bloggername.equals(obj.bloggername) &&
                this.md5HahCode.equals(obj.md5HahCode) && this.keyword.equals(obj.keyword) );
    }

    @Override
    public int hashCode() { return (title+postdate+link+description+link+bloggerlink+bloggername+md5HahCode+keyword).hashCode(); }
}
