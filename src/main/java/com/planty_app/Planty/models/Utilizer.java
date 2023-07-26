package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;
import java.util.List;

@Entity
@Getter
@Setter
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
    private String login;
    private Role role=Role.USER;
    @Lob
    private byte[] avatar;
    
    @Column(columnDefinition = "VARCHAR(5000000)")
    private String base64Avatar;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;
    
    //двусторонняя связь для доступа растений
    //из юзера при помощи репозитория
    @OneToMany(mappedBy = "utilizer",cascade = CascadeType.ALL)
    private List<MyPlantSample> myPlantSamples;
    
    public void setBase64Avatar(byte[] image){
        this.base64Avatar= Base64.getEncoder().encodeToString(image);
    }
}
