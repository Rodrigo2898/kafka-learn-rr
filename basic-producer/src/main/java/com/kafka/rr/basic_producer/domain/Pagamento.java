package com.kafka.rr.basic_producer.domain;

import java.math.BigDecimal;

public record Pagamento(Integer numero,
                        String descricao,
                        BigDecimal valor) {}
