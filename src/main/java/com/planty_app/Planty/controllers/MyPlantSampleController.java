package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.MyPlantSample;
import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.services.MyPlantSampleService;
import com.planty_app.Planty.services.UtilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("")
public class MyPlantSampleController {
    private final MyPlantSampleService myPlantSampleService;
    private final UtilizerService utilizerService;
    
    @Autowired
    public MyPlantSampleController(MyPlantSampleService myPlantSampleService,
                                   UtilizerService utilizerService) {
        this.myPlantSampleService = myPlantSampleService;
        this.utilizerService = utilizerService;
    }
    
    @GetMapping("/myGarden")
    public String findAllSamplePlants(Principal principal,
                                      Model model) {
        Utilizer currentUtilizer=utilizerService.findUtilizerByLogin(principal.getName());
        model.addAttribute("utilizer", currentUtilizer);
        List<MyPlantSample> plantSamples = myPlantSampleService.findAllPlantSamples(currentUtilizer);
        model.addAttribute("plantSamples", plantSamples);
        return "pages/myGarden";
    }
}