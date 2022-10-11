package com.naverapi.naverapi.article.component.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.naverapi.naverapi.article.application.service.apirequest.NaverApiRequestService;
import com.naverapi.naverapi.article.application.service.notification.NotificationService;
import com.naverapi.naverapi.article.application.service.email.EmailService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailStatus;
import com.naverapi.naverapi.article.domain.email.EmailType;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.user.application.service.UserService;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverApiScheduler {

    @Autowired
    NaverApiRequestService naverApiRequestService;

    @Autowired
    private EventPublisher publisher;
    private final EmailEventQueue eventQueue;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final UserService userService;

    private final KeyWordRepository keyWordRepository;

    @Scheduled( cron = "0 */1 * * * *")
    public void getAllKeyAndUpdateBlog() throws JsonProcessingException {

        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        HashSet<KeyWord> keyWordHashSet = new HashSet<>();

        for ( UserResponseDto dto : userResponseDtoList ) {
            List<KeyWord> keyWordList = keyWordRepository.findByUserId(dto.getId());
            keyWordHashSet.addAll(keyWordList);
        }

        Iterator<KeyWord> iter = keyWordHashSet.iterator();
        while(iter.hasNext()) {
            KeyWord key = iter.next();
            naverApiRequestService.getBlogContentsSortByDate(key.getKeyword());
            naverApiRequestService.getCafeContentsSortByDate(key.getKeyword());
            naverApiRequestService.getNewsContentsSortByDate(key.getKeyword());
        }
    }

    @Scheduled( cron = "0 */1 * * * *")
    public void getAllUserAndSendEmail() throws InterruptedException, JsonProcessingException {
        // 모든 유저를 조회한다.
        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        for ( UserResponseDto dto : userResponseDtoList) {
            // 해당 유저 이메일 보낸다.
            Email email = Email.builder()
                            .address(dto.getEmail())
                            .title("test")
                            .message("테스트입니다.")
                            .build();
            // 이메일 이벤트 생성
            publisher.publish(EmailEvent.of(EmailStatus.STANDBY, EmailType.BLOG, "test", email));
        }
    }

    @Async("taskScheduler")
    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailService, userService, notificationService )
                .run();
    }

}
