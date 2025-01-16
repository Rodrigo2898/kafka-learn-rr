package com.kafka.rr.stockserviceconsumer.consumer;

import com.kafka.rr.stockserviceconsumer.domain.Order;
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
            groupId = "stock-group",
            properties = {"spring.json.value.default.type=com.kafka.rr.stockserviceconsumer.domain.Order"})
    public void consumeOrderCreatedEvent(Order order, Acknowledgment ack) {
        logger.info("Consuming order created event: {} ", order.orderId());
        ack.acknowledge();
    }
}
