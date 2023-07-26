package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.*;
import com.planty_app.Planty.services.MyPlantSampleService;
import com.planty_app.Planty.services.PlantService;
import com.planty_app.Planty.services.UtilizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class MyPlantSampleController {
    private final MyPlantSampleService myPlantSampleService;
    private final UtilizerService utilizerService;
    private final PlantService plantService;
    
    @Autowired
    public MyPlantSampleController(MyPlantSampleService myPlantSampleService,
                                   UtilizerService utilizerService,
                                   PlantService plantService) {
        this.myPlantSampleService = myPlantSampleService;
        this.utilizerService = utilizerService;
        this.plantService = plantService;
    }
    
    @GetMapping("/myGarden")
    public String findAllSamplePlants(Principal principal,
                                      Model model) {
        Utilizer currentUtilizer=utilizerService.findUtilizerByLogin(principal.getName());
        model.addAttribute("utilizer", currentUtilizer);
        
        List<MyPlantSample> plantSamples = myPlantSampleService.findAllPlantSamples(currentUtilizer);
        model.addAttribute("plantSamples", plantSamples);
        
        List<Task> pending=plantSamples.stream()
                .flatMap(a->a.getThisPlantTasks().stream())
                .filter(el-> el.getTaskStatus().equals(TaskStatus.PENDING))
                .toList();
        model.addAttribute("pendingTasks", pending);
        
        List<Task> completed=plantSamples.stream()
                .flatMap(a->a.getThisPlantTasks().stream())
                .filter(el-> el.getTaskStatus().equals(TaskStatus.COMPLETED))
                .toList();
        model.addAttribute("completedTasks", completed);
        
        List<Task> undone=plantSamples.stream()
                .flatMap(a->a.getThisPlantTasks().stream())
                .filter(el-> el.getTaskStatus().equals(TaskStatus.UNDONE))
                .toList();
        model.addAttribute("undoneTasks", undone);
        return "pages/myGarden";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletePlantSample/{plantId}")
    public String deletePlantSample(HttpServletRequest request,
                                    @PathVariable Long plantId) {
        HttpSession session = request.getSession();
        Utilizer chosenUser = (Utilizer) session.getAttribute("chosenUser");
        myPlantSampleService.deletePlantSampleById(plantId);
        return "forward:/userInfo/" + chosenUser.getId();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping("/addPlantToMyGarden/{plantId}")
    public String addPlantToMyGarden(@PathVariable Long plantId,
                                     @RequestParam("age") String age,
                                     Principal principal) {
        Utilizer currentUser = utilizerService.findUtilizerByLogin(principal.getName());
        Plant currentPlant = plantService.findPlantById(plantId);
        myPlantSampleService.createNewPlantSample(currentUser, currentPlant, age);
        return "redirect:/myGarden";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/deletePlantFromGarden/{plantId}")
    public String deletePlantFromGarden(@PathVariable Long plantId){
        myPlantSampleService.deletePlantSampleById(plantId);
        return "redirect:/myGarden";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping("/updateTasks")
    public String updateTasks(@RequestParam(value = "completedTasks", required = false) List<String> tasks){
        if(tasks!=null)
            myPlantSampleService.tasksAnalizator(tasks);
        return "redirect:/myGarden";
    }
}