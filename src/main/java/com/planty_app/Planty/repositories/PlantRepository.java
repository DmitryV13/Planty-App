package com.planty_app.Planty.repositories;

import com.planty_app.Planty.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}