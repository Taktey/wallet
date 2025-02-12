package com.example.wallet.controller;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public void doTransaction(@Valid @RequestBody OperationDTO transaction) {
        walletService.doTransaction(transaction);
    }

    @GetMapping("/{id}")
    public int getWalletAmount(@PathVariable UUID id){
        return walletService.getWalletAmount(id);
    }

}
