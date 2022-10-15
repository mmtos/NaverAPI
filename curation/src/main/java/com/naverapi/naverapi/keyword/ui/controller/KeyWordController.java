package com.naverapi.naverapi.keyword.ui.controller;

import com.naverapi.naverapi.keyword.application.KeywordService;
import com.naverapi.naverapi.keyword.ui.dto.KeyWordAddRequest;
import com.naverapi.naverapi.user.infra.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class KeyWordController {

    private final KeywordService keywordService;

    @GetMapping("/keyword/view/add")
    public String keyWordAddView(@SessionAttribute SessionUser user, Model model){
        if(user != null){
            model.addAttribute("user",user);
        }
        KeyWordAddRequest keyWordAddRequest = keywordService.getKeyWordsByUserId(user);
        model.addAttribute("keyWordAddRequest",keyWordAddRequest);

        return "keyword/addView";
    }
}
