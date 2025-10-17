package com.example.instructions.controller;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.model.PlatformTrade;
import com.example.instructions.service.TradeService;
import com.example.instructions.service.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final KafkaPublisher kafkaPublisher;

    @PostMapping("/upload")
    public ResponseEntity<List<PlatformTrade>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<PlatformTrade> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                CanonicalTrade trade = CanonicalTrade.builder()
                        .accountNumber(fields[0])
                        .securityId(fields[1])
                        .tradeType(fields[2])
                        .amount(Double.parseDouble(fields[3]))
                        .timestamp(fields[4])
                        .build();
                PlatformTrade transformed = tradeService.processTrade(trade);
                kafkaPublisher.publishTrade(transformed);
                results.add(transformed);
            });
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping
    public ResponseEntity<Map<String, PlatformTrade>> listTrades() {
        return ResponseEntity.ok(tradeService.getAllTrades());
    }
}
