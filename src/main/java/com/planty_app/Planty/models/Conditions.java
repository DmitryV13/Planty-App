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
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private String temperature;//температура
    private String lighting;//освещение
    private String soil;//почва
    private String humidity;//влажность
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Watering watering;//полив
    
    private String fertilizer;//удобрение
    private String transplantation;//пересадка
    private String threats;//угрозы
}
