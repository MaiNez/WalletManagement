package com.sergeymalov.walletmanagement.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column (name = "id")
    private UUID walletId;

    @Column (name = "amount")
    private BigDecimal amount;

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
