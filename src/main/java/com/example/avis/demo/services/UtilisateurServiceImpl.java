package com.example.avis.demo.services;

import com.example.avis.demo.entites.Role;
import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.entites.Validation;
import com.example.avis.demo.enumeration.TypeDeRole;
import com.example.avis.demo.repositories.UtilisateurRepository;
import com.example.avis.demo.exceptions.EmailInvalideException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService{
    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;



    @Override
    public void inscription(Utilisateur utilisateur) throws EmailInvalideException{
        if (!utilisateur.getEmail().contains("@")){
            throw new EmailInvalideException("Email not valide");
        }
        if (!utilisateur.getEmail().contains(".")){
            throw new EmailInvalideException("Email not valide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOptional.isPresent())
            throw new RuntimeException("Votre email est deja utilise");

        String mdpCrypte=this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        Role utilisateurRole = new Role();
        utilisateurRole.setLibelle(TypeDeRole.UTILISATEUR);

        utilisateur.setRole(utilisateurRole);

       utilisateur =  this.utilisateurRepository.save(utilisateur);

       this.validationService.enregistrer(utilisateur);
    }

    @Override
    public void activation(Map<String, String> activation){
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("votre code a expire");
        }
            Utilisateur utilisateurActive = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(()->new RuntimeException("user inconnu"));

        utilisateurActive.setActif(true);
        utilisateurRepository.save(utilisateurActive);
    }
}
