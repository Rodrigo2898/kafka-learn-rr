package com.kafka.rr.advancedstreamskafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${kafka.process.picture.topic}")
    private String pictureTopic;

    @Bean
    public NewTopic pictureTopic() {
        return TopicBuilder
                .name(pictureTopic)
                .partitions(1)
                .replicas(1)
                .config("min.insync.replicas", "2")
                .build();
    }
}
