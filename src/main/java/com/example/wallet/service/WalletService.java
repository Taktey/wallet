package com.example.wallet.service;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.NoSuchWalletFoundException;
import com.example.wallet.exception.UnknownTransactionTypeException;
import com.example.wallet.mapper.OperationMapper;
import com.example.wallet.model.OperationEntity;
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
    public void doTransaction(OperationDTO operationDTO) {
        OperationEntity operation = OperationMapper.getInstance().operationDTOToEntity(operationDTO);

        WalletEntity wallet = walletRepository.findById(operation.getWalletId())
                .orElseThrow(NoSuchWalletFoundException::new);
        int updatedAmount;
        switch (operation.getOperationType()) {
            case DEPOSIT -> updatedAmount = wallet.getAmount() + operation.getAmount();
            case WITHDRAW -> {
                if (wallet.getAmount() < operation.getAmount()) {
                    throw new InsufficientFundsException();
                }
                updatedAmount = wallet.getAmount() - operation.getAmount();
            }
            default -> throw new UnknownTransactionTypeException(); // Не должна падать, защита  некорректного раширения списка операций
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
