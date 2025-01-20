package com.kafka.rr.advancedproducer.controller;

import com.kafka.rr.advancedproducer.domain.InputData;
import com.kafka.rr.advancedproducer.producer.SolProducer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolController {

    private final SolProducer solProducer;

    public SolController(SolProducer solProducer) {
        this.solProducer = solProducer;
    }

    @PostMapping("/sol")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getLargestPicture(@RequestBody InputData data, HttpServletRequest request) {
        var address = request.getRemoteAddr();
        solProducer.send(data, address);
    }
}
