package com.naverapi.naverapi.apirequest.domain.meta;

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
@Entity(name = "API_REQUEST_META")
public class ApiRequestMeta extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = false)
    private String keyword;

    @Column(length = 256, nullable = true)
    private String lastBuildDate;

    @Column(length = 256, nullable = true)
    private Long total;

    @Column(length = 1024, nullable = true)
    private String requestUrl;

    @Builder
    public ApiRequestMeta(String keyword, String lastBuildDate, Long total, String requestUrl) {
        this.keyword = keyword;
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.requestUrl = requestUrl;
    }
}
