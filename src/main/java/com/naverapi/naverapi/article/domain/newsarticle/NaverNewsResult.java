package com.naverapi.naverapi.article.domain.newsarticle;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "NEWSARTICLE")
@NoArgsConstructor
public class NaverNewsResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String title;

    @Column(length = 1024, nullable = false)
    private String originallink;

    @Column(length = 1024, nullable = false)
    private String link;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(length = 1024, nullable = false)
    private String pubDate;

    @Column(length = 1024, nullable = false)
    private String md5HahCode;

    @Builder
    public NaverNewsResult(String title, String originallink, String link,
                           String description, String pubDate, String md5HahCode) {
        this.title = title;
        this.originallink = originallink;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.md5HahCode = md5HahCode;
    }

}
