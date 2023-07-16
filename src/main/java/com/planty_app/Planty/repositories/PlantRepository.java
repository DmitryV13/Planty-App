package com.planty_app.Planty.repositories;

import com.planty_app.Planty.models.Plant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Transactional
    List<Plant> findAll();
    
    @Transactional
    Optional<Plant> findPlantById(Long id);
    
    @Transactional
    void deletePlantById(Long id);
    
    @Transactional
    List<Plant> findPlantsByName(String name);
}