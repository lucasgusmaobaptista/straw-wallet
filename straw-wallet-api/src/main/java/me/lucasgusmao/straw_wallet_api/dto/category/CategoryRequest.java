package me.lucasgusmao.straw_wallet_api.dto.category;

import jakarta.validation.constraints.NotBlank;
import me.lucasgusmao.straw_wallet_api.model.enums.CategoryType;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryRequest(
        @NotBlank(message = "Campo obrigatório!")
        String name,
        CategoryType type,
        String icon,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
