package com.naverapi.naverapi.apirequest.schedule.dto;

import com.naverapi.naverapi.apirequest.domain.meta.ApiRequestMeta;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseSaveDto {

    private String keyword;

    private String lastBuildDate;

    private Long total;

    private String url;

    private JSONArray item;

    public ApiRequestMeta toEntity(){
        return ApiRequestMeta.builder()
                .keyword(keyword)
                .lastBuildDate(lastBuildDate)
                .total(total)
                .requestUrl(url)
                .build();
    }

    @Builder
    public ApiResponseSaveDto( String keyword, String lastBuildDate, Long total, String url, JSONArray item) {
        this.keyword = keyword;
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.url = url;
        this.item = item;
    }

}
