package com.example.avis.demo.repositories;

import com.example.avis.demo.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByEmail(String email);
}
