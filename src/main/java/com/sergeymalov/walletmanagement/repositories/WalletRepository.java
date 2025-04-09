package com.sergeymalov.walletmanagement.repositories;

import com.sergeymalov.walletmanagement.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
@Repository

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Query("SELECT w.amount FROM Wallet w WHERE w.walletId = :walletId")
    Optional<BigDecimal> findAmountByWalletId(@Param("walletId") UUID walletId);

    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount + :amount WHERE w.walletId = :walletId")
    int incrementAmount(@Param("walletId") UUID walletId, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount - :amount WHERE w.walletId = :walletId AND w.amount >= :amount")
    int decrementAmountIfSufficientBalance(@Param("walletId") UUID walletId, @Param("amount") BigDecimal amount);
}
