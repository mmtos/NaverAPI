package com.naverapi.naverapi.article.component.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.naverapi.naverapi.article.application.service.apirequest.NaverApiRequestService;
import com.naverapi.naverapi.article.application.service.article.ArticleService;
import com.naverapi.naverapi.article.application.service.notification.NotificationService;
import com.naverapi.naverapi.article.application.service.email.EmailService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.*;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.article.ui.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverCafeResultResponseDto;
import com.naverapi.naverapi.article.ui.dto.NaverNewsResultResponseDto;
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

import java.util.ArrayList;
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

    private final ArticleService articleService;

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
            naverApiRequestService.getBlogContentsSortByDate(key.getKeyword(), 1);
            naverApiRequestService.getCafeContentsSortByDate(key.getKeyword(), 1);
            naverApiRequestService.getNewsContentsSortByDate(key.getKeyword(), 1);
        }
    }

    @Scheduled( cron = "0 */59 * * * *")
    public void getAllUserAndSendEmail() throws InterruptedException, JsonProcessingException {
        // 모든 유저를 조회한다.
        List<NaverBlogArticleResponseDto> bList = new ArrayList<>();
        List<NaverCafeResultResponseDto> cList = new ArrayList<>();
        List<NaverNewsResultResponseDto> nList = new ArrayList<>();

        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        for ( UserResponseDto dto : userResponseDtoList) {
            List<KeyWord> keyWordList = keyWordRepository.findByUserId(dto.getId());
            Iterator<KeyWord> iter = keyWordList.iterator();

            while(iter.hasNext()) {
                KeyWord key = iter.next();
                bList.addAll(articleService.getBlogArticleListForSendEmail(key.getKeyword()));
                cList.addAll(articleService.getCafeArticleListForSendEmail(key.getKeyword()));
                nList.addAll(articleService.getNewsArticleListForSendEmail(key.getKeyword()));
            }

            MailContent mailContent = MailContent.builder()
                    .user(dto.toEntity())
                    .bList(bList)
                    .cList(cList)
                    .nList(nList)
                    .build();

            // 해당 유저 이메일 보낸다.
            Email email = Email.builder()
                            .address(dto.getEmail())
                            .title("test")
                            .message("테스트입니다.")
                            .build();
            // 이메일 이벤트 생성
            publisher.publish(EmailEvent.of(EmailStatus.STANDBY, EmailType.BLOG, "test", email, mailContent));
        }
    }

    @Async("taskScheduler")
    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailService, notificationService )
                .run();
    }

}
