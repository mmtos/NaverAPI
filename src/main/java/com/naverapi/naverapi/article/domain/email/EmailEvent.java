package com.naverapi.naverapi.article.domain.email;

import lombok.Value;

@Value(staticConstructor = "of")
public class EmailEvent {

    EmailStatus staus;

    EmailType type;

    String typeDetail;

    Email email;

    public static  EmailEvent create(EmailType type, String typeDetail, Email email) {
        return EmailEvent.of(EmailStatus.STANDBY, type, typeDetail, email);
    }

    public EmailEvent updateStatus(EmailStatus updateStatus) {
        return EmailEvent.of(updateStatus, type, typeDetail, email);
    }

    public boolean isStandby() {
        return staus == EmailStatus.STANDBY;
    }

    public boolean isQueueWait() {
        return staus == EmailStatus.QUEUE_WAIT;
    }
}
