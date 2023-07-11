package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String external;
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String fragmentation;
    
    private ProtectionStatus protectionStatus;
}
