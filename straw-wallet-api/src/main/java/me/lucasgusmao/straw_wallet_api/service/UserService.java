package me.lucasgusmao.straw_wallet_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.UserDTO;
import me.lucasgusmao.straw_wallet_api.mappers.UserMapper;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.UserRepository;
import me.lucasgusmao.straw_wallet_api.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserValidator userValidator;

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        User newUser = mapper.toEntity(userDTO);

        String username = extractUsernameFromEmail(newUser.getEmail());
        newUser.setUsername(username);
        userValidator.validate(newUser);
        newUser.setActivationToken(UUID.randomUUID().toString());

        User savedUser = repository.save(newUser);
        return mapper.toDTO(savedUser);
    }

    private String extractUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
