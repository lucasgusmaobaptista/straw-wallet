package me.lucasgusmao.straw_wallet_api.dto.income;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record IncomeRequest(
        @NotBlank(message = "Campo obrigatório!")
        String name,
        String icon,
        BigDecimal amount,
        String categoryName,
        UUID categoryId,
        UUID userId,
        LocalDate date,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
