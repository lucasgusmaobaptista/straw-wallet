package me.lucasgusmao.straw_wallet_api.dto;

public record AuthDTO(
        String username,
        String password,
        String token
) {
}
