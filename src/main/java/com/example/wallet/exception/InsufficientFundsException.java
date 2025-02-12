package com.example.wallet.exception;

public class InsufficientFundsException extends RuntimeException{
    private static final String INSUFFICIENT_FUNDS_EXCEPTION_MSG = "Insufficient funds";

    public InsufficientFundsException(){super(INSUFFICIENT_FUNDS_EXCEPTION_MSG);}
}
