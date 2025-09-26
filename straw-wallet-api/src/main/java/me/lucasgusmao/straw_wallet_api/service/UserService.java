package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.user.AuthDTO;
import me.lucasgusmao.straw_wallet_api.dto.user.UserRequest;
import me.lucasgusmao.straw_wallet_api.dto.user.UserResponse;
import me.lucasgusmao.straw_wallet_api.event.UserRegisteredEvent;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.InvalidCredentialsException;
import me.lucasgusmao.straw_wallet_api.mappers.UserMapper;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.UserRepository;
import me.lucasgusmao.straw_wallet_api.utils.JwtUtils;
import me.lucasgusmao.straw_wallet_api.validator.UserValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserValidator userValidator;
    private final PasswordEncoder encoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public UserResponse register(UserRequest userDTO) {
        User newUser = mapper.toEntity(userDTO);

        String username = extractUsernameFromEmail(newUser.getEmail());
        newUser.setUsername(username);
        userValidator.validate(newUser);
        newUser.setActivationToken(UUID.randomUUID().toString());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        User savedUser = repository.save(newUser);

        applicationEventPublisher.publishEvent(new UserRegisteredEvent(savedUser));

        UserResponse response = mapper.toResponse(savedUser);
        return response;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return repository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário: " +   authentication.getName() + " não encontrado"));
    }

    public UserResponse getUser(String username) {
        User currentUser;
        if (username == null) {
            currentUser = getCurrentUser();
        } else {
            currentUser = repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário: " + username + " não encontrado"));
        }
        return mapper.toResponse(currentUser);
    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDTO dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
            String token = jwtUtils.generateJwtToken(authenticate);
            return Map.of(
                    "token", token,
                    "user", getUser(dto.username())
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Uduário ou senha inválidos");
        }
    }

    public boolean activateProfile(String activationToken) {
        return repository.findByActivationToken(activationToken)
                .map(user -> {
                    user.setIsActive(true);
                    repository.save(user);
                    return true;
                }).orElse(false);
    }

    public boolean isUserActive(String username) {
        return repository.findByUsername(username)
                .map(User::getIsActive)
                .orElse(false);
    }

    private String extractUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
