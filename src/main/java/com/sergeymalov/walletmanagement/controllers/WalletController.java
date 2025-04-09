package com.sergeymalov.walletmanagement.controllers;
import com.sergeymalov.walletmanagement.dto.WalletDto;
import com.sergeymalov.walletmanagement.serveces.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(name = "Wallet Management", description = "API для управления кошельками")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Изменение баланса кошелька",
            description = "Позволяет увеличить или уменьшить баланс кошелька",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Операция выполнена успешно"),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Кошелек не найден",
                            content = @Content),
                    @ApiResponse(responseCode = "422", description = "Недостаточно средств",
                            content = @Content)
            }
    )
    public void changeAmount(@RequestBody @Valid WalletDto walletDto) {
        walletService.changeAmount(walletDto);
    }

    @GetMapping("/{id}/balance")
    @Operation(
            summary = "Получение баланса кошелька",
            description = "Возвращает текущий баланс указанного кошелька",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение баланса",
                            content = @Content(schema = @Schema(implementation = BigDecimal.class))),
                    @ApiResponse(responseCode = "404", description = "Кошелек не найден",
                            content = @Content)
            }
    )
    public BigDecimal getAmount(
            @Parameter(description = "UUID кошелька", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        return walletService.getAmount(id);
    }
}