package com.naverapi.naverapi.notification.event;

import com.naverapi.naverapi.notification.domain.email.EmailEvent;
import com.naverapi.naverapi.notification.domain.email.EmailStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventListener {
    @Autowired
    private final EmailEventQueue eventQueue;

    @EventListener
    public void onEvent(EmailEvent email) {
        if(!email.isStandby()) {
            log.info("Mmail(id:{}) status is not STANDBY!", email.getEmail().getId());
            return;
        }

        while( eventQueue.isFull() ) {
            if(!email.isQueueWait()) {
                email = updateStatus(email,  EmailStatus.QUEUE_WAIT);
            }
        }
        email = updateStatus(email, EmailStatus.QUEUE);
        eventQueue.offer(email);
    }

    private EmailEvent updateStatus(EmailEvent emailEvent, EmailStatus status) {
        EmailStatus beforeStatus = emailEvent.getStaus();
        EmailEvent updatedEmailEvent = emailEvent.updateStatus(status);
        return updatedEmailEvent;
    }
}
