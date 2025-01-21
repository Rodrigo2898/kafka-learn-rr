package com.kafka.rr.advancedstreamskafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@SpringBootApplication
public class AdvancedStreamsKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedStreamsKafkaApplication.class, args);
    }

}
