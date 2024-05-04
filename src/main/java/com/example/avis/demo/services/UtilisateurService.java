package com.example.avis.demo.services;

import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.exceptions.*;

import java.util.Map;

public interface UtilisateurService {
    public void inscription(Utilisateur utilisateur) throws EmailInvalideException;

    void activation(Map<String, String> activation);
}
