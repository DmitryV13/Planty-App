package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Entity
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Utilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private String name;
    private String surname;
    private Role role;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;
    
    //двусторонняя связь для доступа растений
    //из юзера при помощи репозитория
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilizer")
    private List<MyPlantSample> myPlantSamples;
}
