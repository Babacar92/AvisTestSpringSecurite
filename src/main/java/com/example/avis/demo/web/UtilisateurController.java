package com.example.avis.demo.web;


import com.example.avis.demo.dtos.AuthentificationDTO;
import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.exceptions.EmailInvalideException;
import com.example.avis.demo.securite.JwtService;
import com.example.avis.demo.services.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {
    private UtilisateurService utilisateurService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur)throws EmailInvalideException {
        log.info("inscription");
        this.utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String,String> activation){
        this.utilisateurService.activation(activation);

    }

    @PostMapping("/connexion")
    public Map<String,String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));
        if (authenticate.isAuthenticated()){
            return this.jwtService.generate(authentificationDTO.username());
        }
        log.info("resultat"+authenticate.isAuthenticated());
        return null;
    }
}
