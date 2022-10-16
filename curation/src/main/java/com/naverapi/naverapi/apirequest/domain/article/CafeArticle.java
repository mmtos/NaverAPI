package com.naverapi.naverapi.apirequest.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "CAFE_ARTICLE")
@NoArgsConstructor
public class CafeArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String title;

    @Column(length = 1024, nullable = false)
    private String link;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(length = 1024, nullable = false)
    private String cafeName;

    @Column(length = 1024, nullable = false)
    private String cafeUrl;

    @Column(length = 1024, nullable = false)
    private String md5HahCode;

    @Column(length = 256, nullable = false)
    private String keyword;

    @Builder
    public CafeArticle(String title, String link, String description, String cafeName,
                       String cafeUrl, String md5HahCode, String keyword){
        this.title = title;
        this.link = link;
        this.description = description;
        this.cafeName = cafeName;
        this.cafeUrl = cafeUrl;
        this.md5HahCode = md5HahCode;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CafeArticle)) return false;

        CafeArticle obj = (CafeArticle) o;
        return (this.title.equals(obj.title) && this.link.equals(obj.link) && this.description.equals(obj.description) &&
                this.cafeName.equals(obj.cafeName) && this.cafeUrl.equals(obj.cafeUrl) && this.md5HahCode.equals(obj.md5HahCode) &&
                this.keyword.equals(obj.keyword) );
    }

    @Override
    public int hashCode() { return (title+link+description+cafeName+cafeUrl+keyword+md5HahCode).hashCode(); }
}
