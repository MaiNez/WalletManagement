package com.sergeymalov.walletmanagement.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    private final UUID walletId;
    private final BigDecimal currentBalance;
    private final BigDecimal requestedAmount;

    public InsufficientFundsException(UUID walletId, BigDecimal requestedAmount) {
        super(String.format("Недостаточно средств в кошельке %s.", walletId));
        this.walletId = walletId;
        this.requestedAmount = requestedAmount;
        this.currentBalance = null;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
}