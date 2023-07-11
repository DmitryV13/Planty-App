package com.planty_app.Planty.services;

import com.planty_app.Planty.models.MyPlantSample;
import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.repositories.MyPlantSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyPlantSampleService {
    private final MyPlantSampleRepository myPlantSampleRepository;
    @Autowired
    public MyPlantSampleService(MyPlantSampleRepository myPlantSampleRepository){
        this.myPlantSampleRepository=myPlantSampleRepository;
    }
    
    public List<MyPlantSample> findAllPlantSamples(Utilizer utilizer){
        Optional<List<MyPlantSample>> plantList=myPlantSampleRepository.findMyPlantSamplesByUtilizer(utilizer);
        return plantList.get();
    }
    
    public void deleteAllSamplesWithPlant(Plant plant){
        List<MyPlantSample> plantsToDelete=myPlantSampleRepository.findMyPlantSamplesByPlant(plant).get();
        myPlantSampleRepository.deleteAll(plantsToDelete);
    }
    
    public void deletePlantSampleById(Long id){
        myPlantSampleRepository.deleteMyPlantSampleById(id);
    }
    
    public void deletePlantSamplesFromUser(Utilizer user){
        List<Long> plantsToDeleteIds=myPlantSampleRepository.findMyPlantSamplesByUtilizer(user).get()
                .stream()
                .map(pl->pl.getId())
                .toList();
        for (Long id: plantsToDeleteIds) {
            deletePlantSampleById(id);
        }
    }
}