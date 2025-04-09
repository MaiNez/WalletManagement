package com.sergeymalov.walletmanagement.serveces;

import com.sergeymalov.walletmanagement.dto.WalletDto;
import com.sergeymalov.walletmanagement.entity.Wallet;
import com.sergeymalov.walletmanagement.exception.InsufficientFundsException;
import com.sergeymalov.walletmanagement.exception.WalletNotFoundException;
import com.sergeymalov.walletmanagement.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static com.sergeymalov.walletmanagement.dto.OperationTypeEnum.*;

@Service
public class WalletService implements ChangeAmountService<WalletDto> {

    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAmount(UUID id) {
        return repository.findAmountByWalletId(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public WalletDto getById(UUID id) {
        Objects.requireNonNull(id, "ID кошелька не может быть null");
        Wallet wallet = repository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        return mapToDto(wallet);
    }

    @Override
    @Transactional
    public boolean changeAmount(WalletDto walletDto) {
        validateWalletDto(walletDto);


        if (!repository.existsById(walletDto.getWalletId())) {
            throw new WalletNotFoundException(walletDto.getWalletId());
        }

        if (DEPOSIT.equals(walletDto.getOperationType())) {
            return processDeposit(walletDto);
        } else if (WITHDRAW.equals(walletDto.getOperationType())) {
            return processWithdrawal(walletDto);
        }

        throw new IllegalArgumentException("Неподдерживаемый operation type: " + walletDto.getOperationType());
    }

    private boolean processDeposit(WalletDto walletDto) {
        int updatedRows = repository.incrementAmount(
                walletDto.getWalletId(),
                walletDto.getAmount()
        );

        if (updatedRows == 0) {
            throw new WalletNotFoundException(walletDto.getWalletId());
        }

        return true;
    }

    private boolean processWithdrawal(WalletDto walletDto) {
        int updatedRows = repository.decrementAmountIfSufficientBalance(
                walletDto.getWalletId(),
                walletDto.getAmount()
        );

        if (updatedRows == 0) {
            throw new InsufficientFundsException(walletDto.getWalletId(), walletDto.getAmount());
        }

        return true;
    }

    private void validateWalletDto(WalletDto walletDto) {
        if (walletDto == null) {
            throw new IllegalArgumentException("WalletDTO не может быть null null");
        }
        if (walletDto.getWalletId() == null) {
            throw new IllegalArgumentException("ID кошелька не может быть null");
        }
        if (walletDto.getAmount() == null || walletDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Значение должно быть положительным");
        }
        if (walletDto.getOperationType() == null) {
            throw new IllegalArgumentException("Operation type не может быть null");
        }
    }


    public static WalletDto mapToDto(Wallet wallet){
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setAmount(wallet.getAmount());
        return walletDto;
    }
}
