package com.sergeymalov.walletmanagement.controllers;

import com.sergeymalov.walletmanagement.dto.WalletDto;
import com.sergeymalov.walletmanagement.serveces.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;


@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public void changeAmount(@RequestBody WalletDto walletDto){
        walletService.changeAmount(walletDto);
    }

    @GetMapping("/{id}")
    public BigDecimal GetWalletById(@PathVariable UUID id){
        return walletService.getAmount(id);
    }



}
