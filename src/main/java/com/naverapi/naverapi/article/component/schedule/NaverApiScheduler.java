package com.naverapi.naverapi.article.component.schedule;

import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.user.domain.Role;
import com.naverapi.naverapi.user.domain.User;
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

    @Autowired
    NotificationService notificationService;

    @Scheduled( cron = "0 */1 * * * *")
    public void naverApiTest() throws InterruptedException {
        naverApiService.getBlogContentsSortByDate("수리남");

        Thread.sleep(100);
    }
}
