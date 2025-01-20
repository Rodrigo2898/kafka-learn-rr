package com.kafka.rr.advancedproducer.producer;

import com.kafka.rr.advancedproducer.domain.InputData;
import com.kafka.rr.advancedproducer.event.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SolProducer {

    @Value("${kafka.push.topic}")
    private String topic;
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SolProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(InputData data, String address) {
        Message message = Message.newBuilder()
                .setSol(data.sol())
                .setIpAddress(address)
                .setFullName(data.fullName())
                .build();
        kafkaTemplate.send(topic, message);
        logger.info("The message {} was asynchronously sent to kafka", message);
    }
}
