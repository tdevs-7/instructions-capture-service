package com.example.instructions.util;

import com.example.instructions.model.CanonicalTrade;
import com.example.instructions.model.PlatformTrade;

public class TradeTransformer {

    public static PlatformTrade transform(CanonicalTrade trade) {
        String maskedAccount = maskAccount(trade.getAccountNumber());
        String upperSecurity = trade.getSecurityId().toUpperCase();
        String normalizedType = normalizeTradeType(trade.getTradeType());

        return PlatformTrade.builder()
                .platformId("ACCT123")
                .trade(PlatformTrade.TradeDetails.builder()
                        .account(maskedAccount)
                        .security(upperSecurity)
                        .type(normalizedType)
                        .amount(trade.getAmount())
                        .timestamp(trade.getTimestamp())
                        .build())
                .build();
    }

    private static String maskAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.length() <= 4)
            return "****";
        return "*****" + accountNumber.substring(accountNumber.length() - 4);
    }

    private static String normalizeTradeType(String tradeType) {
        if (tradeType == null) return "U";
        switch (tradeType.toLowerCase()) {
            case "buy": return "B";
            case "sell": return "S";
            default: return "U";
        }
    }
}
