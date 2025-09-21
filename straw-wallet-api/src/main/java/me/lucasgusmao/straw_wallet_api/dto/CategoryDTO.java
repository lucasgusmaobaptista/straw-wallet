package me.lucasgusmao.straw_wallet_api.dto;

import jakarta.validation.constraints.NotBlank;
import me.lucasgusmao.straw_wallet_api.model.CategoryType;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDTO(

        @NotBlank(message = "Campo obrigatório!")
        String name,
        CategoryType type,
        String icon,
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
