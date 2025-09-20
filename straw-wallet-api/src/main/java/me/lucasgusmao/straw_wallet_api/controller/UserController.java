package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.UserDTO;
import me.lucasgusmao.straw_wallet_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO dto) {
        UserDTO user = service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
