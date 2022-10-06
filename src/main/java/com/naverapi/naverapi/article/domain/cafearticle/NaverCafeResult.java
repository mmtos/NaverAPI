package com.naverapi.naverapi.article.domain.cafearticle;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "CAFEARTICLE")
@NoArgsConstructor
public class NaverCafeResult {

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

    @Builder
    public NaverCafeResult(String title, String link, String description, String cafeName,
                           String cafeUrl, String md5HahCode){
        this.title = title;
        this.link = link;
        this.description = description;
        this.cafeName = cafeName;
        this.cafeUrl = cafeUrl;
        this.md5HahCode = md5HahCode;
    }
}
