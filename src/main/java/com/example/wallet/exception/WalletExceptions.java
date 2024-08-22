package com.example.wallet.exception;

public class WalletExceptions {

    public static class WalletNotFoundException extends RuntimeException {
        public WalletNotFoundException() {
            super("Wallet not found");
        }
    }

    public static class UnsuccessfulWithdrawException extends RuntimeException {
        public UnsuccessfulWithdrawException() {
            super("Not enough balance");
        }
    }

    public static class InvalidAmountException extends RuntimeException {
        public InvalidAmountException() {
            super("Invalid amount");
        }
    }
}