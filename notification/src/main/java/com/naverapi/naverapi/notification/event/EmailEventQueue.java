package com.naverapi.naverapi.notification.event;

import com.naverapi.naverapi.notification.domain.email.EmailEvent;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailEventQueue {
    private final Queue<EmailEvent> queue;
    private final int queueSize;


    public EmailEventQueue(int queueSize) {
        this.queue = new LinkedBlockingQueue<>(queueSize);
        this.queueSize = queueSize;
    }

    public static EmailEventQueue of(int size){
        return new EmailEventQueue(size);
    }

    public boolean offer(EmailEvent email) {
        boolean returnValue = queue.offer(email);
        healthCheck();
        return returnValue;
    }

    public EmailEvent poll() {
        if(queue.size() <= 0) {
            throw new IllegalStateException("No events in the queue!");
        }
        EmailEvent email = queue.poll();
        healthCheck();
        return email;
    }

    public  boolean isFull(){
        return size() == queueSize;
    }

    public boolean isRemaining() {
        return size() > 0;
    }

    private int size() {
        return queue.size();
    }

    private void healthCheck(){
        log.info("{\"totalQueueSize\":{}, \"currentQueueSize\":{}}", queueSize, size());
    }
}
