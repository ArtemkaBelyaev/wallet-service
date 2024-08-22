package com.example.wallet.controller;

import com.example.wallet.dto.WalletBalanceResponse;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.entity.WalletTransaction;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet() {
        Wallet wallet = walletService.createWallet();
        return ResponseEntity.ok(wallet);
    }

    @PostMapping
    public ResponseEntity<Void> walletTransaction(@RequestBody WalletOperationRequest request) {
        walletService.processTransaction(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable UUID walletId) {
        WalletBalanceResponse balance = walletService.getBalance(walletId);
        return ResponseEntity.ok().body(balance);
    }
}