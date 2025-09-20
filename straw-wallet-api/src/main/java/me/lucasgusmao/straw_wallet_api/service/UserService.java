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
    private final EmailService emailService;

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        User newUser = mapper.toEntity(userDTO);

        String username = extractUsernameFromEmail(newUser.getEmail());
        newUser.setUsername(username);
        userValidator.validate(newUser);
        newUser.setActivationToken(UUID.randomUUID().toString());
        User savedUser = repository.save(newUser);

        String activationLink = "http://localhost:8080/api/v1/activate?token=" + savedUser.getActivationToken();
        String subject = "STRAW WALLET - Falta um passo para ativar sua conta!";
        String body = "Clique no link abaixo e ative sua conta para ter acesso completo à nossa plataforma!\n" + activationLink;
        emailService.sendEmail(newUser.getEmail(), subject, body);

        return mapper.toDTO(savedUser);
    }

    public boolean activateProfile(String activationToken) {
        return repository.findByActivationToken(activationToken)
                .map(user -> {
                    user.setIsActive(true);
                    repository.save(user);
                    return true;
                }).orElse(false);
    }

    private String extractUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
