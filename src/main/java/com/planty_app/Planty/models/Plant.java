package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private String name;
    private String photo;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private History history;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Description description;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Conditions conditions;
    
}