package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Watering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private String amountOfWater;
    private String duration;
    private String period;
}
