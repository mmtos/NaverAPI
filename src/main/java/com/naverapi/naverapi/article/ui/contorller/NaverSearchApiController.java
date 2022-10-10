package com.naverapi.naverapi.article.ui.contorller;

import com.naverapi.naverapi.article.application.service.apirequest.NaverApiRequestService;
import com.naverapi.naverapi.article.component.api.NaverSearchApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/naver")
@Slf4j
public class NaverSearchApiController {

    @Autowired
    NaverSearchApi naverSearchApi = new NaverSearchApi();
    @Autowired
    NaverApiRequestService naverApiRequestService;

    @GetMapping("/test")
    public String test()
    {
        return "hello";
    }

}
