package com.school.management.school_management_system.Scheduler;


import com.school.management.school_management_system.KafkaProducerConsumer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchoolScheduler {
    @Autowired
    private ProducerService producer;

    // every 10 minutes
    @Scheduled(cron = "0 0/10 * * * *")
    public void emitHeartbeat() {
        producer.sendEvent("${kafka.topic}", "heartbeat:" + System.currentTimeMillis());
    }

    // daily summary at midnight (can change for testing)
    @Scheduled(cron = "0 0 0 * * *")
    public void dailySummary() {
        producer.sendEvent("${kafka.topic}", "daily.summary:" + System.currentTimeMillis());
    }
}
