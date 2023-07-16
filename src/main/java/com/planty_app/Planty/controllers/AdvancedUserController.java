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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
public class AdvancedUserController {
    private final PlantService plantService;
    private final MyPlantSampleService myPlantSampleService;
    private final UtilizerService utilizerService;
    
    public AdvancedUserController(PlantService plantService,
                                  MyPlantSampleService myPlantSampleService,
                                  UtilizerService utilizerService) {
        this.plantService = plantService;
        this.myPlantSampleService = myPlantSampleService;
        this.utilizerService = utilizerService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/createNewPlants")
    public String createNewPlants(Model model) {
        model.addAttribute("newPlant", new Plant());
        return "pages/plantsTools";
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
    @PostMapping("/createUser")
    public String createUser(Model model,
                             @ModelAttribute("user") Utilizer newUser,
                             @RequestParam("password") String password,
                             @RequestParam("file") MultipartFile image) {
        try {
            utilizerService.createUtilizer(newUser, password, image);
        } catch (IOException e) {
            model.addAttribute("fileError", e.getMessage());
        }
        return "redirect:/createUsers";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model) {
        List<Utilizer> users = utilizerService.findAllUsers();
        model.addAttribute("users", users);
        return "pages/viewAllUsers";
    }
    
    @GetMapping("/plantInfo/{plantId}")
    public String plantInfo(Model model,
                            @PathVariable Long plantId) {
        Plant chosenPlant = plantService.findPlantById(plantId);
        model.addAttribute("plant", chosenPlant);
        return "pages/plantInfo";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletePlant/{plantId}")
    public String deletePlant(@PathVariable Long plantId) {
        plantService.deletePlantById(plantId);
        return "redirect:/viewAllPlants";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userInfo/{userId}")
    public String userInfo(Model model,
                           HttpServletRequest request,
                           @PathVariable Long userId) {
        Utilizer chosenUser = utilizerService.findUtilizerById(userId);
        HttpSession session = request.getSession();
        session.setAttribute("chosenUser", chosenUser);
        model.addAttribute("user", chosenUser);
        return "pages/userInfo";
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
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        utilizerService.deleteUserById(userId);
        return "redirect:/viewAllUsers";
    }
    
    @PostMapping("/findPlant")
    public String findPlant(HttpServletRequest request,
                            @RequestParam("findPlant") String plantName) {
        List<Plant> result = plantService.findPlantsByName(plantName);
        HttpSession session = request.getSession();
        session.setAttribute("result", result);
        return "redirect:/resultFind";
    }
    
    @GetMapping("/resultFind")
    public String resultFind(HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession();
        List<Plant> result = (List<Plant>) session.getAttribute("result");
        model.addAttribute("result", result);
        return "pages/resultFind";
    }
    
    @PostMapping("/addPlantToMyGarden/{plantId}")
    public String addPlantToMyGarden(@PathVariable Long plantId,
                                     @RequestParam("age") String age,
                                     Principal principal) {
        Utilizer currentUser = utilizerService.findUtilizerByLogin(principal.getName());
        Plant currentPlant = plantService.findPlantById(plantId);
        myPlantSampleService.createNewPlantSample(currentUser, currentPlant, age);
        return "redirect:/myGarden";
    }
    
    @GetMapping("/plantView/{plantId}")
    public String plantViewForUser(Model model,
                                   @PathVariable Long plantId) {
        Plant chosenPlant = plantService.findPlantById(plantId);
        model.addAttribute("plant", chosenPlant);
        return "pages/plantInfoBeforeAdd";
    }
    
    @GetMapping("/deletePlantFromGarden/{plantId}")
    public String deletePlantFromGarden(@PathVariable Long plantId){
        myPlantSampleService.deletePlantSampleById(plantId);
        return "redirect:/myGarden";
    }
}
