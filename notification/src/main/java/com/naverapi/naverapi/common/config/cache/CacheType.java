package com.naverapi.naverapi.common.config.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum CacheType {

    BLOG_ARTICLE("blogArticleList", (20 * 60), 10000),
    CAFE_ARTICLE("cafeArticleList", (20 * 60), 10000),
    NEWS_ARTICLE("newsArticleList", (20 * 60), 10000);

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize){
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;



}
