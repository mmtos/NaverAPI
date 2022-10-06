package com.naverapi.naverapi.keyword.ui.dto;

import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.user.domain.User;
import com.naverapi.naverapi.user.infra.SessionUser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeyWordAddRequest {
    private String userKeyWordList;
    private static final String TOKEN_SEPARATOR = ",";

    public KeyWordAddRequest(String userKeyWordList) {
        this.userKeyWordList = userKeyWordList;
    }

    public List<KeyWord> toKeyWordList(SessionUser user) {
        return Arrays.stream(userKeyWordList.split(","))
                .map((token) -> new KeyWord(token,user.getId()))
                .collect(Collectors.toList());
    }
}
