package com.planty_app.Planty.repositories;

import com.planty_app.Planty.models.MyPlantSample;
import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.models.Utilizer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPlantSampleRepository extends JpaRepository<MyPlantSample, Long> {
    @Transactional
    Optional<List<MyPlantSample>> findMyPlantSamplesByUtilizer(Utilizer utilizer);
    
    @Transactional
    Optional<List<MyPlantSample>> findMyPlantSamplesByPlant(Plant plant);
    
    @Transactional
    void deleteMyPlantSampleById(Long id);
    
    @Transactional
    Optional<MyPlantSample> findMyPlantSampleById(Long id);
}