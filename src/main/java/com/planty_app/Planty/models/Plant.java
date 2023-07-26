package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;

@Entity
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private String name;
    private byte[] photo;
    
    @Column(columnDefinition = "VARCHAR(5000000)")
    String base64Photo;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private History history;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Description description;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Conditions conditions;
    
    public void setBase64Photo(byte[] image){
        this.base64Photo=Base64.getEncoder().encodeToString(image);
    }
}