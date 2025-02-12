package com.example.wallet.controller;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.exception.UnknownTransactionTypeException;
import com.example.wallet.model.OperationType;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/wallet")
public class WalletController {
    private final WalletService walletService;


    @PostMapping
    public ResponseEntity<String> doTransaction(@Valid @RequestBody OperationDTO transaction) {
        walletService.doTransaction(transaction);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public int getWalletAmount(@PathVariable UUID id) {
        return walletService.getWalletAmount(id);
    }

}
