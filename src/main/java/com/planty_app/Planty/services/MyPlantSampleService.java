package com.planty_app.Planty.services;

import com.planty_app.Planty.models.*;
import com.planty_app.Planty.repositories.MyPlantSampleRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MyPlantSampleService {
    private final MyPlantSampleRepository myPlantSampleRepository;
    @Autowired
    public MyPlantSampleService(MyPlantSampleRepository myPlantSampleRepository) {
        this.myPlantSampleRepository = myPlantSampleRepository;
    }
    
    public List<MyPlantSample> findAllPlantSamples(Utilizer utilizer) {
        Optional<List<MyPlantSample>> plantList = myPlantSampleRepository.findMyPlantSamplesByUtilizer(utilizer);
        return plantList.get();
    }
    
    public void deleteAllSamplesWithPlant(Plant plant) {
        List<MyPlantSample> plantsToDelete = myPlantSampleRepository.findMyPlantSamplesByPlant(plant).get();
        myPlantSampleRepository.deleteAll(plantsToDelete);
    }
    
    public void deletePlantSampleById(Long id) {
        myPlantSampleRepository.deleteMyPlantSampleById(id);
    }
    
    public void deletePlantSamplesFromUser(Utilizer user) {
        List<Long> plantsToDeleteIds = myPlantSampleRepository.findMyPlantSamplesByUtilizer(user).get()
                .stream()
                .map(pl -> pl.getId())
                .toList();
        for (Long id : plantsToDeleteIds) {
            deletePlantSampleById(id);
        }
    }
    
    public void createNewPlantSample(Utilizer user, Plant plant, String age) {
        MyPlantSample newPlantSample = new MyPlantSample()
                .withPlant(plant)
                .withUtilizer(user)
                .withInitialPlantAge(Integer.parseInt(age))
                .withStartOfWatering(LocalDateTime.now());
        newPlantSample.setDaysBeforeNextWatering(newPlantSample.getPlant().getConditions().getWatering().getPeriodInt());
        newPlantSample.findCurrentAgeInterval();
        
        LocalDateTime deadlineForCurrentTask = LocalDateTime.now().plusDays(newPlantSample.getDaysBeforeNextWatering());
        Task newTask = new Task()
                .withMyPlantSample(newPlantSample)
                .withTaskName("Need to be watered")
                .withTaskDetails(newPlantSample.findCurrentAmountOfWater())
                .withPlantName(newPlantSample.getPlant().getName())
                .withFullPlantAge(newPlantSample.getFullPlantAge())
                .withTaskStatus(TaskStatus.PENDING)
                .withNeedToBeDoneAt(deadlineForCurrentTask);
        
        newPlantSample.addPlantTask(newTask);
        myPlantSampleRepository.save(newPlantSample);

    }

    public void addNewTask(MyPlantSample sample){
        LocalDateTime deadlineForCurrentTask = LocalDateTime.now().plusDays(sample.getDaysBeforeNextWatering());
        Task newTask = new Task()
                .withMyPlantSample(sample)
                .withTaskName("Need to be watered")
                .withTaskDetails(sample.findCurrentAmountOfWater())
                .withPlantName(sample.getPlant().getName())
                .withFullPlantAge(sample.getFullPlantAge())
                .withTaskStatus(TaskStatus.PENDING)
                .withNeedToBeDoneAt(deadlineForCurrentTask);
        
        sample.addPlantTask(newTask);
    }
    
    @Synchronized
    @Scheduled(cron = "@daily")
    public void refreshDaysBeforeNextWateringForAllPlantSamples(){
        List<MyPlantSample> plants=myPlantSampleRepository.findAll();
        if(plants.isEmpty())
            return;
        for (MyPlantSample el: plants) {
            if(el.refreshDaysBeforeNextWatering()){
                this.addNewTask(el);
            }
            myPlantSampleRepository.save(el);
        }
    }
    
    @Scheduled(cron = "@daily")
    public void refreshCareTimeForAll(){
        List<MyPlantSample> plants=myPlantSampleRepository.findAll();
        if(plants.isEmpty())
            return;
        for (MyPlantSample el: plants) {
            el.refreshCareTime();
            myPlantSampleRepository.save(el);
        }
    }
    
    public void markTaskAsCompleted(MyPlantSample sample, Long taskId){
        if(sample.taskCompleted(taskId)) {
            this.addNewTask(sample);
            myPlantSampleRepository.save(sample);
        }
    }
    
    /**
     * There is possibility to mark completed task by id (second value in the array plantIdTaskId),
     * but current version is not destined to have more than 1 pending task for each plant sample
     */
    @Synchronized
    public void tasksAnalizator(List<String> tasks){
        for (String taskEl:tasks) {
            String[] plantIdTaskId=taskEl.split("DELIMITER");
            MyPlantSample plantWithThisTask=myPlantSampleRepository.findMyPlantSampleById(Long.parseLong(plantIdTaskId[0])).get();
            this.markTaskAsCompleted(plantWithThisTask, Long.parseLong(plantIdTaskId[1]));
        }
    }
}