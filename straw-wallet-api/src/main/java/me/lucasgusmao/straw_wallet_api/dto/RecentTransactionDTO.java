package me.lucasgusmao.straw_wallet_api.dto;

import lombok.Builder;
import me.lucasgusmao.straw_wallet_api.model.enums.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RecentTransactionDTO(
        UUID id,
        UUID userId,
        String icon,
        String name,
        BigDecimal amount,
        LocalDate date,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CategoryType categoryType
        ) {
}
