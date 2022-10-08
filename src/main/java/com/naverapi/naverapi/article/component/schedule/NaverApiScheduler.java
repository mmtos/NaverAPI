package com.naverapi.naverapi.article.component.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.application.service.event.EmailEventWorker;
import com.naverapi.naverapi.article.component.event.EventPublisher;
import com.naverapi.naverapi.article.domain.email.*;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.keyword.domain.KeyWord;
import com.naverapi.naverapi.keyword.domain.KeyWordRepository;
import com.naverapi.naverapi.user.application.service.UserService;
import com.naverapi.naverapi.user.domain.User;
import com.naverapi.naverapi.user.ui.contorller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    NaverApiService naverApiService;

    @Autowired
    private EventPublisher publisher;
    private final EmailEventQueue eventQueue;
    private final EmailRepository emailRepository;
    private final NotificationService notificationService;

    private final UserService userService;

    private final KeyWordRepository keyWordRepository;

    @Scheduled( cron = "0 */5 * * * *")
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
            naverApiService.getBlogContentsSortByDate(key.getKeyword());
        }
    }

    @Scheduled( cron = "0 */1 * * * *")

    public void getAllUserAndSendEmail() throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        // 모든 유저를 조회한다.
        List<UserResponseDto> userResponseDtoList = userService.findAllDesc();
        for ( UserResponseDto dto : userResponseDtoList) {
            log.info(mapper.writeValueAsString(dto));
            // 해당 유저 이메일 보낸다.
            Email email = Email.builder()
                            .address(dto.getEmail())
                            .title("test")
                            .message("테스트입니다.")
                            .build();
            User testUser = dto.toEntity();
            testUser.addEmail(email);
            email.setUser(testUser);
            // 이메일 이벤트 생성
            publisher.publish(EmailEvent.of(EmailStatus.STANDBY, EmailType.BLOG, "test", email));
        }

    }

    @Scheduled(fixedRate = 100)
    public void schedule() {
        new EmailEventWorker( eventQueue, emailRepository, notificationService )
                .run();
    }

}
