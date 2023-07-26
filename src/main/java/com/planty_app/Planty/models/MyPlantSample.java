package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    private Plant plant;
    
    @ManyToOne
    @JoinColumn(name = "utilizer_id")
    private Utilizer utilizer;
    
    /**
    Represents the index in the array Amount of water key, which tell us the end
    of current watering period
    */
    private int currentAgeInterval;
    private int daysBeforeNextWatering;
    private LocalDateTime startOfWatering;
    /**
     * is stored in days
     */
    private int plantAge;
    private int careTimeInDays = 0;
    
    /**
     * Always current pending task will be last
     */
    @OneToMany(mappedBy = "myPlantSample",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> thisPlantTasks=new ArrayList<>();
    
    public void findCurrentAgeInterval() {
        int[] aowk = this.getPlant().getConditions().getWatering().getAmountOfWaterKeyInt();
        for (int i = 1; i < aowk.length; i++) {
            if (plantAge+careTimeInDays > aowk[i]) {
                i++;
            } else {
                this.currentAgeInterval = i;
                return;
            }
        }
        this.currentAgeInterval = aowk.length - 1;
    }
    
    public int refreshDaysBeforeNextWatering() {
        int newTaskIsNeeded=0;
        this.findCurrentAgeInterval();
        if(this.daysBeforeNextWatering<0){
            //BUUUGS    daysBeforeNextWatering DONT BECAME POSITIVE
            this.thisPlantTasks.get(this.thisPlantTasks.size()-1).setTaskStatus(TaskStatus.UNDONE);
            this.daysBeforeNextWatering=this.getPlant().getConditions().getWatering().getPeriodInt();
            newTaskIsNeeded=1;
        }
        this.daysBeforeNextWatering--;
        return newTaskIsNeeded;
    }
    
    public void refreshCareTime(){
        this.careTimeInDays++;
    }
    
    public void addPlantTask(Task newTask){
        this.thisPlantTasks.add(newTask);
    }
    
    public void taskCompleted(Long taskId){
        for (Task taskEl: this.thisPlantTasks) {
            if(taskEl.getId()==taskId) {
                taskEl.setTaskStatus(TaskStatus.COMPLETED);
                break;
            }
        }
        this.daysBeforeNextWatering+=this.getPlant().getConditions().getWatering().getPeriodInt();
    }
}
