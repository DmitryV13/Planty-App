package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.services.MyPlantSampleService;
import com.planty_app.Planty.services.PlantService;
import com.planty_app.Planty.services.UtilizerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class AdvancedUserController {
    private final PlantService plantService;
    private final UtilizerService utilizerService;
    
    public AdvancedUserController(PlantService plantService,
                                  UtilizerService utilizerService) {
        this.plantService = plantService;
        this.utilizerService = utilizerService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/createNewPlants")
    public String createNewPlants(Model model) {
        model.addAttribute("newPlant", new Plant());
        return "pages/plantsTools";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewAllPlants")
    public String viewAllPlants(Model model) {
        List<Plant> plants = plantService.findAllPlants();
        model.addAttribute("allPlants", plants);
        return "pages/viewAllPlants";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/createUsers")
    public String createUsers(Model model) {
        model.addAttribute("user", new Utilizer());
        return "pages/usersTools";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model) {
        List<Utilizer> users = utilizerService.findAllUsers();
        model.addAttribute("users", users);
        return "pages/viewAllUsers";
    }
}
