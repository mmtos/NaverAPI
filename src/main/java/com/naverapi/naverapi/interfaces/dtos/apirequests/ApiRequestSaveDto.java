package com.naverapi.naverapi.interfaces.dtos.apirequests;

import com.naverapi.naverapi.domain.apirequests.ApiRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class ApiRequestSaveDto {

    private String title;

    private String description;

    private String lastBuildDate;

    private Long total;

    private String url;

    public ApiRequest toEntity(){
        return ApiRequest.builder()
                .lastBuildDate(lastBuildDate)
                .total(total)
                .url(url)
                .build();
    }

    @Builder
    public ApiRequestSaveDto(String lastBuildDate, Long total, String url) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.url = url;
    }

}
