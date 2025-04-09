package com.sergeymalov.walletmanagement.repositories;

import com.sergeymalov.walletmanagement.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository <Wallet, UUID>{
    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount + :amount WHERE w.id = :walletId")
    void incrementAmount(@Param("walletId") UUID walletId, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount - :amount WHERE w.id = :walletId AND w.amount >= :amount")
    int decrementAmount(@Param("walletId") UUID walletId, @Param("amount") BigDecimal amount);
}
