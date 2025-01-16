package com.kafka.rr.order_producer.domain;

import java.util.Date;

public record Order(Integer orderId,
                    Date date) {
}
