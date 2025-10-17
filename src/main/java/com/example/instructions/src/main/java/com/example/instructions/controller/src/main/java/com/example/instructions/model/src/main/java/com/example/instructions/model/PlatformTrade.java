package com.example.instructions.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformTrade {
    private String platformId;
    private TradeDetails trade;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TradeDetails {
        private String account;
        private String security;
        private String type;
        private double amount;
        private String timestamp;
    }
}
