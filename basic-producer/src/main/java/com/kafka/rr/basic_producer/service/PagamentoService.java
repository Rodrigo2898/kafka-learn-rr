package com.kafka.rr.basic_producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.rr.basic_producer.domain.Pagamento;
import com.kafka.rr.basic_producer.producer.PagamentoRequestProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRequestProducer pagamentoRequestProducer;
    private final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    public PagamentoService(PagamentoRequestProducer pagamentoRequestProducer) {
        this.pagamentoRequestProducer = pagamentoRequestProducer;
    }

    public void criar(Pagamento pagamento) {
        try {
            pagamentoRequestProducer.sendMessage(pagamento);
            logger.info("Pagamento criado com sucesso!");
        } catch (JsonProcessingException e) {
            logger.error("Erro ao criar pagamento: {}", e.getMessage());
        }
    }
}
