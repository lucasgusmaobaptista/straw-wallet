package me.lucasgusmao.straw_wallet_api.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        String name,
        String icon,
        BigDecimal amount,
        String categoryName,
        UUID categoryId,
        LocalDate date,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}