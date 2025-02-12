package com.example.wallet.service;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.NoSuchWalletFoundException;
import com.example.wallet.exception.UnknownTransactionTypeException;
import com.example.wallet.model.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private UUID walletId;
    private WalletEntity wallet;

    @BeforeEach
    void setUp() {
        walletId = UUID.randomUUID();
        wallet = new WalletEntity();
        wallet.setId(walletId);
        wallet.setAmount(500);
    }

    @Test
    void doTransactionDepositSuccess() {
        OperationDTO operation = new OperationDTO(walletId.toString(), "DEPOSIT", 200);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.doTransaction(operation);

        assertEquals(700, wallet.getAmount());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void doTransactionWithdrawSuccess() {
        OperationDTO operation = new OperationDTO(walletId.toString(), "WITHDRAW", 200);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.doTransaction(operation);

        assertEquals(300, wallet.getAmount());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void doTransactionWithdrawInsufficientFunds() {
        OperationDTO operation = new OperationDTO(walletId.toString(), "WITHDRAW", 600);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () -> walletService.doTransaction(operation));
        verify(walletRepository, never()).save(wallet);
    }

    @Test
    void doTransactionWalletNotFound() {
        OperationDTO operation = new OperationDTO(walletId.toString(), "DEPOSIT", 100);
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(NoSuchWalletFoundException.class, () -> walletService.doTransaction(operation));
    }

    @Test
    void getWalletAmountSuccess() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        int amount = walletService.getWalletAmount(walletId);
        assertEquals(500, amount);
    }

    @Test
    void getWalletAmountWalletNotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        assertThrows(NoSuchWalletFoundException.class, () -> walletService.getWalletAmount(walletId));
    }
}
