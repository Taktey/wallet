package com.example.wallet.service;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.NoSuchWalletFoundException;
import com.example.wallet.exception.UnknownTransactionTypeException;
import com.example.wallet.model.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void doTransaction(OperationDTO transaction) {
        WalletEntity wallet = walletRepository.findById(UUID.fromString(transaction.getWalletId()))
                .orElseThrow(NoSuchWalletFoundException::new);
        int updatedAmount;
        switch (transaction.getOperationType()) {
            case DEPOSIT -> updatedAmount = wallet.getAmount() + transaction.getAmount();
            case WITHDRAW -> {
                if (wallet.getAmount() < transaction.getAmount()) {
                    throw new InsufficientFundsException();
                }
                updatedAmount = wallet.getAmount() - transaction.getAmount();
            }
            default -> throw new UnknownTransactionTypeException();
        }
        wallet.setAmount(updatedAmount);
        walletRepository.save(wallet);
    }

    public int getWalletAmount(UUID id) {
        WalletEntity wallet = walletRepository.findById(id)
                .orElseThrow(NoSuchWalletFoundException::new);
        return wallet.getAmount();
    }
}
