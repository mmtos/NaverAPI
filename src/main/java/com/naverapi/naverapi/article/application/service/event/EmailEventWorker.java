package com.naverapi.naverapi.article.application.service.event;

import com.naverapi.naverapi.article.application.service.NotificationService;
import com.naverapi.naverapi.article.application.service.email.EmailService;
import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailEvent;
import com.naverapi.naverapi.article.domain.email.EmailRepository;
import com.naverapi.naverapi.article.domain.event.EmailEventQueue;
import com.naverapi.naverapi.article.ui.dto.EmailSaveDto;
import com.naverapi.naverapi.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class EmailEventWorker implements Runnable{

    private final EmailEventQueue eventQueue;

    private final EmailService emailService;

    private final UserService userService;

    private final NotificationService notificationService;

    @Override
    public void run() {
        if(eventQueue.isRemaining()) {
            EmailEvent emailEvent = eventQueue.poll();
            // 이벤트에서 이메일 객체 임시 저장
            Email emailNeedToSend = emailEvent.getEmail();
            // 이메일 정보로 user를 조회하여 메일 발송
            String result  = notificationService.sendNotificationByEmail(
                    userService.findUserByEmail(emailNeedToSend.getAddress()).toEntity());
            // 보낸 이메일과 발송 결과 저장
            Email email =  emailService.saveEmailEntity( EmailSaveDto.builder()
                            .address(emailNeedToSend.getAddress())
                            .title(emailNeedToSend.getTitle())
                            .message(emailNeedToSend.getMessage())
                            .result(result)
                            .build() );

            log.info( email.getTitle() + " " + email.getAddress() + " " + email.getMessage() );
            log.info( result );
        }
    }
}
