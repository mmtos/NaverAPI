package com.naverapi.naverapi.article.component.schedule;

import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailRepository;
import com.naverapi.naverapi.article.domain.email.EmailType;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.user.domain.Role;
import com.naverapi.naverapi.user.domain.User;
import com.naverapi.naverapi.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverApiScheduler {

//    @Autowired
//    NaverApiService naverApiService;

    @Autowired
    private EventPublisher publisher;

    private final UserRepository userRepository;
    private final EmailEventQueue eventQueue;
    private final EmailRepository emailRepository;
    private final NotificationService notificationService;

    @Scheduled( cron = "0 */1 * * * *")
    public void naverApiTest() throws InterruptedException {

    }

    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailRepository, notificationService )
                .run();
    }

}
