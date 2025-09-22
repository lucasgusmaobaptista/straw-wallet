package me.lucasgusmao.straw_wallet_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.AuthDTO;
import me.lucasgusmao.straw_wallet_api.dto.user.UserRequest;
import me.lucasgusmao.straw_wallet_api.dto.user.UserResponse;
import me.lucasgusmao.straw_wallet_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest dto) {
        UserResponse user = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        boolean result = service.activateProfile(token);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Conta ativada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao ativar conta: Token não encontrado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid AuthDTO dto) {
        try {
            if (!service.isUserActive(dto.username())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        Map.of("error", "O usuário informado não foi ativado. Por favor, verifique seu e-mail e ative sua conta."));
            }
            Map<String, Object> response = service.authenticateAndGenerateToken(dto);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
