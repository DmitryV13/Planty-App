package com.planty_app.Planty.repositories;

import com.planty_app.Planty.models.MyPlantSample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPlantSampleRepository extends JpaRepository<MyPlantSample, Long> {
}