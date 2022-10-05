package com.naverapi.naverapi.article.domain.apirequest;

import com.naverapi.naverapi.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "apirequest")
public class ApiRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String lastBuildDate;

    @Column(length = 256, nullable = false)
    private Long total;

    @Column(length = 1024, nullable = false)
    private String url;

    @Builder
    public ApiRequest(String lastBuildDate, Long total, String url) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.url = url;
    }
}
