package me.lucasgusmao.straw_wallet_api.dto.user;

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
