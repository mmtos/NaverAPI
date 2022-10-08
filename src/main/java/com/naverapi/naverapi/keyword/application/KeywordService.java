package com.naverapi.naverapi.keyword.application;

import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.keyword.ui.dto.KeyWordAddRequest;
import com.naverapi.naverapi.keyword.ui.dto.KeyWordResponseDto;
import com.naverapi.naverapi.user.infra.SessionUser;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeyWordRepository keyWordRepository;

    public void addKeyword(KeyWordAddRequest keyWordAddRequest, SessionUser user){
        List<KeyWord> userKeywords = keyWordAddRequest.toKeyWordList(user);
        keyWordRepository.deleteByUserId(user.getId());
        keyWordRepository.saveAll(userKeywords);
    }

    public KeyWordAddRequest getKeyWordsByUserId(SessionUser user) {
        List<KeyWord> userKeyWords = keyWordRepository.findByUserId(user.getId());
        return new KeyWordAddRequest(joinedKeywords(userKeyWords));
    }

    private String joinedKeywords(List<KeyWord> keyWords){
       return keyWords.stream().map(KeyWord::getKeyword).collect(Collectors.joining(","));
    }

}
