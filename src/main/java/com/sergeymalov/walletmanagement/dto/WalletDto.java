package com.sergeymalov.walletmanagement.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO для изменения баланса кошелька")
public class WalletDto {


        @Schema(description = "UUID кошелька", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        @NotNull
        private UUID walletId;

        @Schema(description = "Сумма операции", example = "100.50", required = true)
        @NotNull
        private BigDecimal amount;

        @Schema(description = "Тип операции: DEPOSIT или WITHDRAW", example = "DEPOSIT", required = true)
        @NotNull
        private OperationTypeEnum operationType;


    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }
}
