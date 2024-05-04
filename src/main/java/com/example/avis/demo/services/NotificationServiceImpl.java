package com.example.avis.demo.services;

import com.example.avis.demo.entites.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    JavaMailSender javaMailSender;

    @Override
    public void envoyer(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@kadam.tech");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Code activation");
        String text = String.format("Bonjour %s,<br/> votre code d'activation est %s; A Bientot",
                validation.getUtilisateur().getNom(),
                validation.getCode());

        message.setText(text);

        javaMailSender.send(message);

    }
}
