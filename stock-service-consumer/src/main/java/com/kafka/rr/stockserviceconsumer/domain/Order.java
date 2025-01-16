package com.kafka.rr.stockserviceconsumer.domain;

import java.util.Date;

public record Order(Integer orderId,
                    Date date) {
}
