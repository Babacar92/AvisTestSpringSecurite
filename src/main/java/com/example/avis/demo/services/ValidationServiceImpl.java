package com.example.avis.demo.services;


import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.entites.Validation;
import com.example.avis.demo.repositories.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService{
    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    @Override
    public void enregistrer(Utilisateur utilisateur){
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        validation.setCreation(Instant.now());
        Instant expiration = Instant.now().plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d",randomInteger);

        validation.setCode(code);

        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    @Override
    public Validation lireEnFonctionDuCode(String code) {
       return this.validationRepository.findByCode(code).orElseThrow(()->new RuntimeException("votre code est invalide"));
    }
}
