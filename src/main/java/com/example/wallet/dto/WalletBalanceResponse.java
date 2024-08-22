package com.example.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WalletBalanceResponse {
    private UUID walletId;
    private long balance;
}