package com.kafka.rr.paymentserviceconsumer.consumer;

import com.kafka.rr.paymentserviceconsumer.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ConsumerOrderCreatedEvent {

    private final Logger logger = LoggerFactory.getLogger(ConsumerOrderCreatedEvent.class);

    @KafkaListener(
            topics = "order-created-event",
            groupId = "payment-group",
            properties = {"spring.json.value.default.type=com.kafka.rr.paymentserviceconsumer.domain.Order"})
    public void consumeOrderCreatedEvent(Order order, Acknowledgment ack) {
        logger.info("Consumed order via payment service: {}", order.orderId());
        ack.acknowledge();
    }
}
