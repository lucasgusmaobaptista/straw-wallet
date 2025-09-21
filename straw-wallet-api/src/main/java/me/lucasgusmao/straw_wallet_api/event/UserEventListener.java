package me.lucasgusmao.straw_wallet_api.event;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.service.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        User user = event.user();

        String activationLink = "http://localhost:8080/api/v1/activate?token=" + user.getActivationToken();
        String subject = "STRAW WALLET - Falta um passo para ativar sua conta!";
        String body = "Clique no link abaixo e ative sua conta para ter acesso completo à nossa plataforma!\n" + activationLink;
        emailService.sendEmail(user.getEmail(), subject, body);
    }
}
