package com.naverapi.naverapi.article.domain.email;

public enum EmailStatus {
    STANDBY,
    QUEUE_WAIT,
    QUEUE,
    PROGRESS,
    SEND,
    SUCCESS,
    FAILURE
}
