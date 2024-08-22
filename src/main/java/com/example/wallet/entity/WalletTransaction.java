package com.example.wallet.entity;

import com.example.wallet.dto.WalletOperationRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class WalletTransaction {
    @Id
    private UUID id;

    @ManyToOne
    private Wallet wallet;
    private String operationType;
    private long amount;

    public WalletTransaction(WalletOperationRequest request) {
        this.id = UUID.randomUUID();
        this.wallet = new Wallet();
        this.wallet.setId(request.getWalletId());
        this.operationType = request.getOperationType().name();
        this.amount = request.getAmount();
    }
}