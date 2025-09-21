package me.lucasgusmao.straw_wallet_api.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDTO(

        @NotBlank(message = "Campo obrigatório!")
        String name,
        String type,
        String icon,
        @NotBlank(message = "Campo obrigatório!")
        UUID userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
