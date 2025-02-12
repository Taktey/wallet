package com.example.wallet.controller;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void doTransactionSuccess() throws Exception {
        OperationDTO transactionDTO = new OperationDTO(
                UUID.randomUUID().toString(),
                "DEPOSIT",
                100
        );

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        verify(walletService, times(1)).doTransaction(any(OperationDTO.class));
    }

    @Test
    void doTransactionInvalidWalletId() throws Exception {
        OperationDTO transactionDTO = new OperationDTO(
                "invalid-uuid",
                "DEPOSIT",
                100
        );

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.walletId").value("walletId должен быть в формате UUID"));

        verify(walletService, never()).doTransaction(any());
    }

    @Test
    void doTransactionInvalidAmount() throws Exception {
        OperationDTO transactionDTO = new OperationDTO(
                UUID.randomUUID().toString(),
                "DEPOSIT",
                0
        );

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("amount должен быть больше 0"));

        verify(walletService, never()).doTransaction(any());
    }

    @Test
    void doTransactionUnknownTransactionType() throws Exception {
        OperationDTO transactionDTO = new OperationDTO(
                UUID.randomUUID().toString(),
                "UNKNOWN_TYPE",
                100
        );

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.operationType").value("operationType должен быть DEPOSIT или WITHDRAWAL"));

        verify(walletService, never()).doTransaction(any());
    }

    @Test
    void getWalletAmountSuccess() throws Exception {
        UUID walletId = UUID.randomUUID();
        int expectedAmount = 500;

        when(walletService.getWalletAmount(walletId)).thenReturn(expectedAmount);

        mockMvc.perform(get("/api/v1/wallet/" + walletId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedAmount)));

        verify(walletService, times(1)).getWalletAmount(walletId);
    }
}
