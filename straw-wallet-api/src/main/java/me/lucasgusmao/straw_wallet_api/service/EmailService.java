package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.EmailSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_ADDRESS}")
    private String mailAddress;

    public void sendEmail(String toAddress, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailAddress);
            message.setTo(toAddress);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException("Falha ao enviar email para: " + toAddress, e);
        }
    }
}
