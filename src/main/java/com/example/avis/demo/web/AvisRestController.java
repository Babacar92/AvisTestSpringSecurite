package com.example.avis.demo.web;


import com.example.avis.demo.entites.Avis;
import com.example.avis.demo.services.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AvisRestController {
    private AvisService avisService;

    @PostMapping("/avis")
    public void creer(@RequestBody Avis avis){
        this.avisService.creerAvis(avis);

    }

}
