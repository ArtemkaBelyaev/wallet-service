package com.example.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WalletExceptionHandler {

    @ExceptionHandler(WalletExceptions.WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWalletNotFoundException(WalletExceptions.WalletNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WalletExceptions.UnsuccessfulWithdrawException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnsuccessfulWithdrawException(WalletExceptions.UnsuccessfulWithdrawException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WalletExceptions.InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidAmountException(WalletExceptions.InvalidAmountException ex) {
        return ex.getMessage();
    }
}