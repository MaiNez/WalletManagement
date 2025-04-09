package com.sergeymalov.walletmanagement.serveces;

import com.sergeymalov.walletmanagement.dto.WalletDto;
import com.sergeymalov.walletmanagement.entity.Wallet;
import com.sergeymalov.walletmanagement.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sergeymalov.walletmanagement.dto.OperationTypeEnum.*;

@Service
public class WalletService implements ChangeAmountService <WalletDto>{

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public BigDecimal getAmount(UUID id) {
        return getById(id).getAmount();
    }

    @Override
    public WalletDto getById(UUID id) {
        Wallet wallet = repository.findById(id).orElseThrow();
        return mapToDto(wallet);
    }


    @Override
    @Transactional
    public boolean changeAmount(WalletDto walletDto) {
        if (walletDto.getOperationType().equals(DEPOSIT)) {
            repository.incrementAmount(walletDto.getWalletId(), walletDto.getAmount());
            return true;
        } else if (walletDto.getOperationType().equals(WITHDRAW)) {
            int updated = repository.decrementAmount(walletDto.getWalletId(), walletDto.getAmount());
            return updated > 0;
        }
        return false;
    }


    public static WalletDto mapToDto(Wallet wallet){
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setAmount(wallet.getAmount());
        return walletDto;
    }
}
