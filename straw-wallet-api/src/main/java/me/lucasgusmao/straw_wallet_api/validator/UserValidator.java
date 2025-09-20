package me.lucasgusmao.straw_wallet_api.validator;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.AlreadyExistsException;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validate(User user) {
        if(existsUserByUsername(user) && existsUserByEmail(user)) {
            throw new AlreadyExistsException("Usuário com email e username já cadastrados");
        } else if (existsUserByUsername(user) && !existsUserByEmail(user)) {
            throw new AlreadyExistsException("Usuário com username já cadastrado");
        } else if (!existsUserByUsername(user) && existsUserByEmail(user)) {
            throw new AlreadyExistsException("Usuário com email já cadastrado");
        }
    }

    public boolean existsUserByUsername(User user) {
        Optional<User> optionalUser = repository.findByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            if (user.getId() != null) {
                return !optionalUser.get().getId().equals(user.getId());
            }
            return true;
        }
        return false;
    }

    public boolean existsUserByEmail(User user) {
        Optional<User> optionalUser = repository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            if (user.getId() != null) {
                return !optionalUser.get().getId().equals(user.getId());
            }
            return true;
        }
        return false;
    }
}
