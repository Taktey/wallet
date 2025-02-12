package com.example.wallet.dto;

import com.example.wallet.model.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationDTO {

    @NotNull(message = "walletId не может быть null")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "walletId должен быть в формате UUID"
    )
    private String walletId;

    @NotNull(message = "operationType обязателен")
    private OperationType operationType;

    @Min(value = 1, message = "amount должен быть больше 0")
    private int amount;
}
