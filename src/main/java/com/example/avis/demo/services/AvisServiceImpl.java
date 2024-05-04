package com.example.avis.demo.services;


import com.example.avis.demo.entites.Avis;
import com.example.avis.demo.repositories.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class AvisServiceImpl implements AvisService {

  private AvisRepository avisRepository;

@Override
  public void creerAvis(Avis avis){
      this.avisRepository.save(avis);
  }


}
