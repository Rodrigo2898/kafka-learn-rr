package com.kafka.rr.basic_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.rr.basic_producer.domain.Pagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PagamentoRequestProducer {

    @Value("${topicos.pagamento.request.topic}")
    public String topicoPagamentoRequest;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(PagamentoRequestProducer.class);

    public PagamentoRequestProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Pagamento pagamento) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(pagamento);
        kafkaTemplate.send(topicoPagamentoRequest, orderAsMessage);
        logger.info("Mensagem enviada");
    }
}
