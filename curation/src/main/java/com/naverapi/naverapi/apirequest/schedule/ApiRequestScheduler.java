package com.naverapi.naverapi.apirequest.schedule;

import com.naverapi.naverapi.apirequest.application.service.NaverApiRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiRequestScheduler {
    private final NaverApiRequestService naverApiRequestService;

    @Scheduled( cron = "0 */1 * * * *")
    public void getAllKeyAndUpdateBlog(){
        // 키워드를 받아와서
        // article 을 얻기위해 api 호출합니다.
    }

}
