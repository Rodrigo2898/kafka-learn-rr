package com.kafka.rr.basic_consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentosRequestConsumer {

    private final Logger logger = LoggerFactory.getLogger(PagamentosRequestConsumer.class);

    @KafkaListener(
            topics = "${topicos.pagamento.request.topic}",
            groupId = "pagamento-request")
    public void consumeMessage(String message) {
        logger.info("Mensagem recebida: {}", message);
    }
}
