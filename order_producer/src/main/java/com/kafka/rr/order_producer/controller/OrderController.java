package com.kafka.rr.order_producer.controller;

import com.kafka.rr.order_producer.domain.Order;
import com.kafka.rr.order_producer.producer.OrderCreatedEventProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderCreatedEventProducer orderCreatedEventProducer;

    public OrderController(OrderCreatedEventProducer orderCreatedEventProducer) {
        this.orderCreatedEventProducer = orderCreatedEventProducer;
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orderCreatedEventProducer.produce(order);
        return "Order created";
    }
}
