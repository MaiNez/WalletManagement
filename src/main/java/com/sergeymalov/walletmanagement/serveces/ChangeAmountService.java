package com.sergeymalov.walletmanagement.serveces;

import java.math.BigDecimal;
import java.util.UUID;
public interface ChangeAmountService<T>{
    T getById(UUID id);
    void changeAmount (T walletDto);
    BigDecimal getAmount (UUID id);
}
