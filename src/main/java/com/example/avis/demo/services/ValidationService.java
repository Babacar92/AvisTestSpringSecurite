package com.example.avis.demo.services;

import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.entites.Validation;

public interface ValidationService {
    public void enregistrer(Utilisateur utilisateur);

    Validation lireEnFonctionDuCode(String code);
}
