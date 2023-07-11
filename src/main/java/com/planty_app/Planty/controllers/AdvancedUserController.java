package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.Plant;
import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.services.MyPlantSampleService;
import com.planty_app.Planty.services.PlantService;
import com.planty_app.Planty.services.UtilizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class AdvancedUserController {
    private final PlantService plantService;
    private final MyPlantSampleService myPlantSampleService;
    private final UtilizerService utilizerService;
    
    public AdvancedUserController(PlantService plantService,
                                  MyPlantSampleService myPlantSampleService,
                                  UtilizerService utilizerService){
        this.plantService=plantService;
        this.myPlantSampleService = myPlantSampleService;
        this.utilizerService = utilizerService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/createNewPlants")
    public String createNewPlants(Model model){
        model.addAttribute("newPlant", new Plant());
        return "pages/plantsTools";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/createNewPlant")
    public String createNewPlant(@ModelAttribute("newPlant") Plant newPlant,
                                 @RequestParam("plantPeriod") String period){
        plantService.createNewPlant(newPlant, period);
        return "redirect:/createNewPlants";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewAllPlants")
    public String viewAllPlants(Model model){
        List<Plant> plants=plantService.findAllPlants();
        model.addAttribute("allPlants", plants);
        return "pages/viewAllPlants";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/createUsers")
    public String createUsers(Model model){
        model.addAttribute("user", new Utilizer());
        return "pages/usersTools";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") Utilizer newUser,
                             @RequestParam("password") String password){
        utilizerService.createUtilizer(newUser, password);
        return "redirect:/createUsers";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model){
        List<Utilizer> users=utilizerService.findAllUsers();
        model.addAttribute("users", users);
        return "pages/viewAllUsers";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plantInfo/{plantId}")
    public String plantInfo(Model model,
                            @PathVariable Long plantId){
        Plant chosenPlant=plantService.findPlantById(plantId);
        model.addAttribute("plant", chosenPlant);
        return "pages/plantInfo";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletePlant/{plantId}")
    public String deletePlant(@PathVariable Long plantId){
        plantService.deletePlantById(plantId);
        return "redirect:/viewAllPlants";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userInfo/{userId}")
    public String userInfo(Model model,
                            HttpServletRequest request,
                            @PathVariable Long userId){
        Utilizer chosenUser=utilizerService.findUtilizerById(userId);
        HttpSession session= request.getSession();
        session.setAttribute("chosenUser", chosenUser);
        model.addAttribute("user", chosenUser);
        return "pages/userInfo";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletePlantSample/{plantId}")
    public String deletePlantSample(HttpServletRequest request,
                                    @PathVariable Long plantId){
        HttpSession session= request.getSession();
        Utilizer chosenUser=(Utilizer)session.getAttribute("chosenUser");
        myPlantSampleService.deletePlantSampleById(plantId);
        return "forward:/userInfo/"+chosenUser.getId();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId){
        utilizerService.deleteUserById(userId);
        return "redirect:/viewAllUsers";
    }
}
