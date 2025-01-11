package com.kafka.rr.basic_producer.api;

import com.kafka.rr.basic_producer.domain.Pagamento;
import com.kafka.rr.basic_producer.service.PagamentoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoAPI {

    private final PagamentoService pagamentoService;

    public PagamentoAPI(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public HttpEntity<?> criar (@RequestBody Pagamento pagamento) {
        pagamentoService.criar(pagamento);
        return ResponseEntity.ok().build();
    }
}