package com.naverapi.naverapi.user.ui.contorller.dto;

import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.user.domain.Role;
import com.naverapi.naverapi.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private List<Email> emailList;

    @Builder
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
        this.emailList = user.getEmailList();
    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
