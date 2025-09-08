package com.school.management.school_management_system.KafkaProducerConsumer;


import com.school.management.school_management_system.AuditPresistance.AuditEvent;
import com.school.management.school_management_system.AuditPresistance.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private AuditRepository auditRepository;

    @KafkaListener(topics = "${kafka.topic}", groupId = "school-group")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
        AuditEvent event = new AuditEvent();
        event.setPayload(message);
        auditRepository.save(event);
    }
}

