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
import org.json.simple.JSONArray;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "apirequest")
public class ApiReponse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 256, nullable = true)
    private String lastBuildDate;

    @Column(length = 256, nullable = true)
    private Long total;

    @Column(length = 1024, nullable = true)
    private String requestUrl;

    private JSONArray itemList;

    @Builder
    public ApiReponse(String lastBuildDate, Long total, String requestUrl, JSONArray itemList) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.requestUrl = requestUrl;
        this.itemList = itemList;
    }
}
