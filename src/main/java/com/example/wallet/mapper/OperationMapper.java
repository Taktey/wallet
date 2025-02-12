package com.example.wallet.mapper;

import com.example.wallet.dto.OperationDTO;
import com.example.wallet.model.OperationEntity;
import com.example.wallet.model.OperationType;

import java.util.UUID;

public final class OperationMapper {
    private static OperationMapper operationMapper;

    public static OperationMapper getInstance() {
        synchronized (OperationMapper.class) {
            if (operationMapper == null) {
                operationMapper = new OperationMapper();
            }
        }
        return operationMapper;
    }

    public OperationEntity operationDTOToEntity(OperationDTO operationDTO) {
        return new OperationEntity(
                UUID.fromString(operationDTO.getWalletId()),
                OperationType.valueOf(operationDTO.getOperationType()),
                operationDTO.getAmount());
    }
}
