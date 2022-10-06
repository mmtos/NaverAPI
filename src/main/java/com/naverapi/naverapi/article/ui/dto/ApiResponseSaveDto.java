package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.apirequest.ApiReponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseSaveDto {

    private String title;

    private String description;

    private String lastBuildDate;

    private Long total;

    private String url;

    public ApiReponse toEntity(){
        return ApiReponse.builder()
                .lastBuildDate(lastBuildDate)
                .total(total)
                .requestUrl(url)
                .build();
    }

    @Builder
    public ApiResponseSaveDto(String lastBuildDate, Long total, String url) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.url = url;
    }

}
