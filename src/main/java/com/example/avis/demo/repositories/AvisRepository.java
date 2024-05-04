package com.example.avis.demo.repositories;

import com.example.avis.demo.entites.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis,Long> {
}
