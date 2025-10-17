package com.example.instructions.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanonicalTrade {
    private String accountNumber;
    private String securityId;
    private String tradeType;
    private double amount;
    private String timestamp;
}
