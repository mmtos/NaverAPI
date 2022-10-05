package com.naverapi.naverapi.component.schedule;

import com.naverapi.naverapi.application.service.NaverApiService;
import com.naverapi.naverapi.component.api.NaverSearchApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAsync
@AllArgsConstructor
public class NaverApiScheduler {

    @Autowired
    NaverApiService naverApiService;

    @Async
    @Scheduled( cron = "0 */5 * * * *")
    public void naverApiTest() throws InterruptedException {
        String result = naverApiService.getBlogContentsSortByExac("강릉여행");
        log.info(result);
        Thread.sleep(100);
    }
}
