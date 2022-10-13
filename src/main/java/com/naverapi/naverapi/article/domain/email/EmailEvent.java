package com.naverapi.naverapi.article.domain.email;

import lombok.Value;

@Value(staticConstructor = "of")
public class EmailEvent {

    EmailStatus staus;

    EmailType type;

    String typeDetail;

    Email email;

    MailContent mailContent;

    public static  EmailEvent create(EmailType type, String typeDetail, Email email, MailContent mailContent) {
        return EmailEvent.of(EmailStatus.STANDBY, type, typeDetail, email, mailContent);
    }

    public EmailEvent updateStatus(EmailStatus updateStatus) {
        return EmailEvent.of(updateStatus, type, typeDetail, email, mailContent);
    }

    public boolean isStandby() {
        return staus == EmailStatus.STANDBY;
    }

    public boolean isQueueWait() {
        return staus == EmailStatus.QUEUE_WAIT;
    }
}
