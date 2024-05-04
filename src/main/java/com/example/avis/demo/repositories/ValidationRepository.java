package com.example.avis.demo.repositories;

import com.example.avis.demo.entites.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation,Integer> {
    Optional<Validation> findByCode(String code);
}
