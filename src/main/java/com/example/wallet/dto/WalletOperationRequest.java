package com.example.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WalletOperationRequest {

    public enum OperationType {
        DEPOSIT, WITHDRAW
    }

    private UUID walletId;
    private OperationType operationType;
    private long amount;
}