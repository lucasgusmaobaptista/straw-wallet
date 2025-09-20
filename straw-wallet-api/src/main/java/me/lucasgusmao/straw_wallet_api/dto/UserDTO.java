package me.lucasgusmao.straw_wallet_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        @NotBlank(message = "Campo obrigatório!")
        String name,
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres.")
        String username,
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Email inválido.")
        String email,
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 6, max = 30, message = "A senha deve ter entre 6 e 30 caracteres.")
        String password,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
