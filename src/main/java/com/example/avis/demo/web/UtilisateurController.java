package com.example.avis.demo.web;


import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.exceptions.EmailInvalideException;
import com.example.avis.demo.services.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur)throws EmailInvalideException {
        log.info("inscription");
        this.utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String,String> activation){
        this.utilisateurService.activation(activation);

    }
}
