package com.example.instructions.service;

import com.example.instructions.model.PlatformTrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisher {
    private final KafkaTemplate<String, PlatformTrade> kafkaTemplate;

    public void publishTrade(PlatformTrade trade) {
        log.info("Publishing to Kafka: {}", trade);
        kafkaTemplate.send("instructions.outbound", trade);
    }
}
