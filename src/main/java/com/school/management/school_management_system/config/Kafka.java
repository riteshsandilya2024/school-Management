package com.school.management.school_management_system.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Kafka {

    @Value("${kafka.topic:school.events}")
    private String topic;

    @Bean
    public NewTopic schoolEventsTopic() {
        return new NewTopic(topic, 1, (short)1);
    }
}

