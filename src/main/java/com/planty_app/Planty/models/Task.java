package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "myPlantSample_id")
    private MyPlantSample myPlantSample;
    
    private int plantAge;
    private String plantName;
    private String  taskName;
    private LocalDateTime needToBeDoneAt;
    private TaskStatus taskStatus;
    
}
