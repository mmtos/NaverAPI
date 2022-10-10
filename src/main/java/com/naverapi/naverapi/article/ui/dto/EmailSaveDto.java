package com.naverapi.naverapi.article.ui.dto;

import com.naverapi.naverapi.article.domain.email.Email;
import lombok.Builder;

public class EmailSaveDto {
    private String address;
    private String title;
    private String message;

    private String result;

    public Email toEntity(){
        return Email.builder()
                .address(address)
                .title(title)
                .message(message)
                .result(result)
                .build();
    }

    @Builder
    public EmailSaveDto( String address, String title, String message, String result ) {
        this.address = address;
        this.title = title;
        this.message = message;
        this.result = result;
    }
}
