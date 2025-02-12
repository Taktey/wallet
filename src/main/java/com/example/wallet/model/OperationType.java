package com.example.wallet.model;

import com.example.wallet.exception.UnknownTransactionTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {
    DEPOSIT,
    WITHDRAW;

    @JsonCreator
    public static OperationType fromString(String value) {
        for (OperationType type : OperationType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new UnknownTransactionTypeException();
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
