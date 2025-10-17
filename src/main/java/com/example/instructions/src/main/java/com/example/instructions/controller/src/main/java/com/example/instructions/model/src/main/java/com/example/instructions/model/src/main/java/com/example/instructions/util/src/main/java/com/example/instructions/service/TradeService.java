package com.example.instructions.service;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.model.PlatformTrade;
import com.example.instructions.util.TradeTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TradeService {

    private final ConcurrentHashMap<String, PlatformTrade> inMemoryStore = new ConcurrentHashMap<>();

    public PlatformTrade processTrade(CanonicalTrade trade) {
        PlatformTrade transformed = TradeTransformer.transform(trade);
        inMemoryStore.put(trade.getAccountNumber(), transformed);
        log.info("Stored trade for account: {}", trade.getAccountNumber());
        return transformed;
    }

    public ConcurrentHashMap<String, PlatformTrade> getAllTrades() {
        return inMemoryStore;
    }
}
