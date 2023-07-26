package com.planty_app.Planty.services;

import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.repositories.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final MyPlantSampleService myPlantSampleService;
    
    public PlantService(PlantRepository plantRepository,
                        MyPlantSampleService myPlantSampleService){
        this.plantRepository=plantRepository;
        this.myPlantSampleService = myPlantSampleService;
    }
    public List<Plant> findAllPlants(){
        return plantRepository.findAll();
    }
    
    public Plant findPlantById(Long id){
        return plantRepository.findPlantById(id).get();
    }
    
    public void deletePlantById(Long id){
        Plant plantToDelete=plantRepository.findPlantById(id).get();
        myPlantSampleService.deleteAllSamplesWithPlant(plantToDelete);
        plantRepository.delete(plantToDelete);
    }
    
    public void createNewPlant(Plant plant, String period, MultipartFile image) throws IOException {
        try {
            plant.setPhoto(image.getBytes());
        }catch (IOException e){
            throw new IOException("Something wrong with photo");
        }
        plant.setBase64Photo(plant.getPhoto());
        plant.getConditions().getWatering().setPeriod(period);
        plantRepository.save(plant);
    }
    
    public List<Plant> findPlantsByName(String name){
        return plantRepository.findPlantsByName(name);
    }
}