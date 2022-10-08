package com.naverapi.naverapi.keyword.ui.dto;

import com.naverapi.naverapi.keyword.domain.KeyWord;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Getter
public class KeyWordResponseDto {
    private Long id;
    private String keyWord;
    private Long userId;

    @Builder
    public KeyWordResponseDto(KeyWord keyWord) {
        this.id = keyWord.getId();
        this.keyWord = keyWord.getKeyword();
        this.userId = keyWord.getUserId();
    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }

}
