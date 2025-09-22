package me.lucasgusmao.straw_wallet_api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String username,
        String email,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
