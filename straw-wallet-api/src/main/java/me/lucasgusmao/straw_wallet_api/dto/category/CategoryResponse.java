package me.lucasgusmao.straw_wallet_api.dto.category;

import me.lucasgusmao.straw_wallet_api.model.CategoryType;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        CategoryType type,
        String icon,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
