package me.lucasgusmao.straw_wallet_api.dto.user;

public record AuthDTO(
        String username,
        String password,
        String token
) {
}
