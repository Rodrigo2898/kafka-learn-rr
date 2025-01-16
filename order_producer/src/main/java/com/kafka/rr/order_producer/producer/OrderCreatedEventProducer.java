package com.kafka.rr.order_producer.producer;

import com.kafka.rr.order_producer.domain.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderCreatedEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(Order order) {
        this.kafkaTemplate.send("order-created-event", order);
    }
}
