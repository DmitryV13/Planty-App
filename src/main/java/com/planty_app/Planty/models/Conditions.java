package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String temperature;//температура
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String lighting;//освещение
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String soil;//почва
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String humidity;//влажность
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Watering watering;//полив
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String fertilizer;//удобрение
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String transplantation;//пересадка
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String threats;//угрозы
}
