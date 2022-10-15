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

    @Column(length = 256, nullable = false)
    private String keyword;

    @Builder
    public NaverNewsResult(String title, String originallink, String link,
                           String description, String pubDate, String md5HahCode, String keyword) {
        this.title = title;
        this.originallink = originallink;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.keyword = keyword;
        this.md5HahCode = md5HahCode;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof NaverNewsResult)) return false;

        NaverNewsResult obj = (NaverNewsResult) o;
        return (this.title.equals(obj.title) && this.originallink.equals(obj.originallink) && this.link.equals(obj.link) &&
                this.description.equals(obj.description) && this.pubDate.equals(obj.pubDate) && this.keyword.equals(obj.keyword) &&
                this.md5HahCode.equals(obj.md5HahCode) );
    }

    @Override
    public int hashCode() { return (title+originallink+link+description+pubDate+keyword+md5HahCode).hashCode(); }

}
