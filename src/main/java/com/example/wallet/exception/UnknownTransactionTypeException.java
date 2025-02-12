package com.example.wallet.exception;

public class UnknownTransactionTypeException extends RuntimeException {
    private final static String UNKNOWN_TRANSACTION_TYPE_EXCEPTION_MSG = "Неизвестный тип операции";

    public UnknownTransactionTypeException() {
        super(UNKNOWN_TRANSACTION_TYPE_EXCEPTION_MSG);
    }

    public UnknownTransactionTypeException(String operationTye) {
        super(UNKNOWN_TRANSACTION_TYPE_EXCEPTION_MSG+": "+operationTye);
    }
}
