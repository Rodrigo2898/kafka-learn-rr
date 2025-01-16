package com.kafka.rr.paymentserviceconsumer.domain;

import java.util.Date;

public record Order(Integer orderId,
                    Date date) {
}
