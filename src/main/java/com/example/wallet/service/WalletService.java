package com.example.wallet.service;

import com.example.wallet.dto.WalletBalanceResponse;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.entity.WalletTransaction;
import com.example.wallet.exception.WalletExceptions;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(0);
        return walletRepository.save(wallet);
    }

    private long calculateNewBalance(long currentBalance, WalletOperationRequest request) {
        return request.getOperationType() == WalletOperationRequest.OperationType.DEPOSIT ?
                currentBalance + request.getAmount() :
                currentBalance - request.getAmount();
    }

    public WalletBalanceResponse getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletExceptions.WalletNotFoundException::new);
        return new WalletBalanceResponse(wallet.getId(), wallet.getBalance());
    }

    @Transactional
    public void processTransaction(WalletOperationRequest request) {
        Wallet wallet = walletRepository.findByIdWithLock(request.getWalletId())
                .orElseThrow(WalletExceptions.WalletNotFoundException::new);
        if (request.getAmount() <= 0) {
            throw new WalletExceptions.InvalidAmountException();
        }

        if (request.getOperationType() == WalletOperationRequest.OperationType.WITHDRAW
                && wallet.getBalance() < request.getAmount()) {
            throw new WalletExceptions.UnsuccessfulWithdrawException();
        }

        long newBalance = calculateNewBalance(wallet.getBalance(), request);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction(request);
        walletTransactionRepository.save(transaction);
    }
}