package com.sergeymalov.walletmanagement.serveces;

import com.sergeymalov.walletmanagement.dto.WalletDto;
import com.sergeymalov.walletmanagement.entity.Wallet;
import com.sergeymalov.walletmanagement.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
    public void changeAmount(WalletDto walletDto) {
        WalletDto wallet = getById(walletDto.getWalletId());

        if(walletDto.getOperationType().equals(DEPOSIT)){
            wallet.setAmount(wallet.getAmount().add(walletDto.getAmount()));
            repository.save(mapToEntity(wallet));
        }
        if(walletDto.getOperationType().equals(WITHDRAW)){
            wallet.setAmount(wallet.getAmount().subtract(walletDto.getAmount()));
            repository.save(mapToEntity(wallet));
        }
    }

    public static WalletDto mapToDto(Wallet wallet){
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setAmount(wallet.getAmount());
        return walletDto;
    }
    public static Wallet mapToEntity(WalletDto walletDto){
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletDto.getWalletId());
        wallet.setAmount(walletDto.getAmount());
        return wallet;
    }

}
