package com.sergeymalov.walletmanagement.serveces;

import java.math.BigDecimal;
import java.util.UUID;
public interface ChangeAmountService<T>{
    T getById(UUID id);
    boolean changeAmount (T walletDto);
    BigDecimal getAmount (UUID id);
}
