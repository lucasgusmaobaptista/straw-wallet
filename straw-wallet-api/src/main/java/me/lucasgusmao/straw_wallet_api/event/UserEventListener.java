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
        String subject = "Confirmação de E-mail - Fluxxo App";
        String body =
                "<div class='container' style='max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); overflow: hidden;'>" +
                        "    <div class='header' style='background-color: #4269E2; color: #ffffff; padding: 20px; text-align: center;'>" +
                        "        <h1 class='logo' style='font-size: 24px; font-weight: bold; margin: 0;'>Fluxxo App</h1>" +
                        "    </div>" +
                        "    <div class='content' style='padding: 30px; text-align: center;'>" +
                        "        <h2>Confirmação de E-mail</h2>" +
                        "        <p>Olá, <b>" + user.getName().split(" ")[0] + "</b>!</p>" +
                        "        <p>Seja bem-vindo(a) ao <b>Fluxxo App</b>. Para ativar sua conta e começar a gerenciar suas finanças, clique no botão abaixo:</p>" +
                        "        <a href='" + activationLink + "' button' style='display: inline-block; padding: 12px 25px; margin-top: 20px; background-color: #4269E2; color: #ffffff; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px;'>Confirmar Minha Conta</a>" +
                        "        <p style='margin-top: 30px;'>Se o botão acima não funcionar, entre em contato com nosso suporte:</p>" +
                        "        <p><small><b>contatofluxxoapp@gmail.com</b></small></p>" +
                        "        <p>Este link expirará em <b>10 minutos</b>.</p>" +
                        "    </div>" +
                        "    <div class='footer' style='background-color: #eeeeee; padding: 15px; text-align: center; font-size: 12px; color: #777777;'>" +
                        "        <p>Este é um e-mail automático e você recebeu porque se registrou no Fluxxo App. Por favor, não responda.</p>" +
                        "    </div>" +
                        "</div>";
        emailService.sendEmail(user.getEmail(), subject, body);
    }
}
