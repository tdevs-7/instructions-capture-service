package com.example.instructions.service;

import com.example.instructions.model.CanonicalTrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final TradeService tradeService;
    private final KafkaPublisher kafkaPublisher;

    @KafkaListener(topics = "instructions.inbound", groupId = "trade-group")
    public void consume(CanonicalTrade trade) {
        log.info("Received trade from Kafka: {}", trade);
        var transformed = tradeService.processTrade(trade);
        kafkaPublisher.publishTrade(transformed);
    }
}
