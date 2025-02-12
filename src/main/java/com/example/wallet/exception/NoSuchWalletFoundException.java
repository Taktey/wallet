package com.example.wallet.exception;

public class NoSuchWalletFoundException extends RuntimeException{
    private static final String NO_SUCH_WALLET_FOUND_MSG = "Wallet with provided id not found";
    public NoSuchWalletFoundException(){super(NO_SUCH_WALLET_FOUND_MSG);}
}
