package com.naverapi.naverapi.notification.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsArticleDto {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;
    private String md5HahCode;
    private String keyword;
}
