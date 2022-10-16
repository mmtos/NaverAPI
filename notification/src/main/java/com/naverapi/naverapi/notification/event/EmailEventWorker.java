package com.naverapi.naverapi.notification.event;

import com.naverapi.naverapi.notification.application.NotificationService;
import com.naverapi.naverapi.notification.application.EmailService;
import com.naverapi.naverapi.notification.domain.email.Email;
import com.naverapi.naverapi.notification.domain.email.EmailEvent;
import com.naverapi.naverapi.notification.domain.email.EmailStatus;
import com.naverapi.naverapi.notification.schedule.dto.EmailSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
public class EmailEventWorker implements Runnable{

    private final EmailEventQueue eventQueue;

    private final EmailService emailService;

    private final NotificationService notificationService;

    @Override
    public void run()  {
        if(eventQueue.isRemaining()) {
            EmailEvent emailEvent = eventQueue.poll();
            // 이메일 이벤트 상태
            emailEvent.updateStatus(EmailStatus.PROGRESS);
            // 이벤트에서 이메일 객체 임시 저장
            Email emailNeedToSend = emailEvent.getEmail();
            // 이메일 정보로 user를 조회하여 메일 발송 - 비동기 처리
            CompletableFuture<String> futureResult = notificationService.sendNotificationByEmail(
                    emailEvent.getMailContent() );
            futureResult.join();
            String result = null;

            try {
                result = futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            // 성공 적으로 발송하지 못하면 로직 종료
            if(! result.equals("success") ) {
                emailEvent.updateStatus(EmailStatus.FAILURE);
                return;
            }

            // 상태 업데이트
            emailEvent.updateStatus(EmailStatus.SEND);
            // 보낸 이메일과 발송 결과 저장
            Email email =  emailService.saveEmailEntity( EmailSaveDto.builder()
                            .address(emailNeedToSend.getAddress())
                            .title(emailNeedToSend.getTitle())
                            .message(emailNeedToSend.getMessage())
                            .result(result)
                            .build() );

            emailEvent.updateStatus(EmailStatus.SUCCESS);
            // 성공
            log.info( email.getTitle() + " " + email.getAddress() + " " + email.getMessage() );
            log.info( result );
        }
    }
}
