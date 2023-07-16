package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    private Plant plant;
    
    @ManyToOne
    @JoinColumn(name="utilizer_id")
    private Utilizer utilizer;
    
    private LocalDateTime startOfWatering;
    private int plantAge;   //is stored in days
    private LocalDateTime dateToCompare;
}
