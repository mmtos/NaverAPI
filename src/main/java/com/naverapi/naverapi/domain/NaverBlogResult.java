package com.naverapi.naverapi.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity(name = "NBR")
@NoArgsConstructor
public class NaverBlogResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String title;

    @Column(length = 256, nullable = false)
    private String postdate;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(length = 256, nullable = false)
    private String link;

    @Column(length = 256, nullable = false)
    private String bloggerlink;

    @Column(length = 256, nullable = false)
    private String bloggername;

    @Builder
    public NaverBlogResult(String title, String postdate, String description, String link, String bloggerlink, String bloggername){
        this.title = title;
        this.postdate = postdate;
        this.description = description;
        this.link = link;
        this.bloggerlink = bloggerlink;
        this.bloggername = bloggername;
    }
}
