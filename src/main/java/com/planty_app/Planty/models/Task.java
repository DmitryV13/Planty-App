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
    
    private String fullPlantAge;
    private String plantName;
    private String  taskName;
    private String taskDetails;
    private LocalDateTime needToBeDoneAt;
    private TaskStatus taskStatus;
    
}
