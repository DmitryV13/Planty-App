package com.planty_app.Planty.repositories;

import com.planty_app.Planty.models.Utilizer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizerRepository extends JpaRepository<Utilizer, Long> {
    @Transactional
    Optional<Utilizer> findUtilizerByLogin(String login);
    
    @Transactional
    Optional<Utilizer> findUtilizerById(Long id);
}