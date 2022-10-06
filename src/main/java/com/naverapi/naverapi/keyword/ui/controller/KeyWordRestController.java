package com.naverapi.naverapi.keyword.ui.controller;

import com.naverapi.naverapi.keyword.application.KeywordService;
import com.naverapi.naverapi.keyword.ui.dto.KeyWordAddRequest;
import com.naverapi.naverapi.user.infra.SessionUser;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/keyword")
@RequiredArgsConstructor
@Slf4j
public class KeyWordRestController {

    private final KeywordService keywordService;

    @PostMapping("/add")
    public ResponseEntity<String> addKeyWord(@SessionAttribute SessionUser user, KeyWordAddRequest keyWordAddRequest) throws URISyntaxException {
        keywordService.addKeyword(keyWordAddRequest, user);
        return ResponseEntity.created(new URI("")).body("성공");
    }

}
