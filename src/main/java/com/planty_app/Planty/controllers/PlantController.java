package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.services.PlantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class PlantController {
    private final PlantService plantService;
    public PlantController(PlantService plantService){
        this.plantService=plantService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/createNewPlant")
    public String createNewPlant(Model model,
                                 @ModelAttribute("newPlant") Plant newPlant,
                                 @RequestParam("plantPeriod") String period,
                                 @RequestParam("file") MultipartFile image) {
        try {
            plantService.createNewPlant(newPlant, period, image);
        } catch (IOException e) {
            model.addAttribute("fileError", e.getMessage());
        }
        return "redirect:/createNewPlants";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletePlant/{plantId}")
    public String deletePlant(@PathVariable Long plantId) {
        plantService.deletePlantById(plantId);
        return "redirect:/viewAllPlants";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/plantInfo/{plantId}")
    public String plantInfo(Model model,
                            @PathVariable Long plantId) {
        Plant chosenPlant = plantService.findPlantById(plantId);
        model.addAttribute("plant", chosenPlant);
        return "pages/plantInfo";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/plantView/{plantId}")
    public String plantViewForUser(Model model,
                                   @PathVariable Long plantId) {
        Plant chosenPlant = plantService.findPlantById(plantId);
        model.addAttribute("plant", chosenPlant);
        return "pages/plantInfoBeforeAdd";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping("/findPlant")
    public String findPlant(HttpServletRequest request,
                            @RequestParam("findPlant") String plantName) {
        List<Plant> result = plantService.findPlantsByName(plantName);
        HttpSession session = request.getSession();
        session.setAttribute("result", result);
        return "redirect:/resultFind";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/resultFind")
    public String resultFind(HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession();
        List<Plant> result = (List<Plant>) session.getAttribute("result");
        model.addAttribute("result", result);
        return "pages/resultFind";
    }
}
