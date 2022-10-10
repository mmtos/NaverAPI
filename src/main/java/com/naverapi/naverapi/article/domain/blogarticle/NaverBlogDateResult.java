package com.naverapi.naverapi.article.domain.blogarticle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "BLOGARTICLE")
@NoArgsConstructor
public class NaverBlogDateResult {
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
    public NaverBlogDateResult(String title, String postdate, String description, String link,
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
}
