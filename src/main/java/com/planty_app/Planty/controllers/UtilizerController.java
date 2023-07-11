package com.planty_app.Planty.controllers;

import com.planty_app.Planty.services.UtilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class UtilizerController {
    private final UtilizerService utilizerService;
    
    @Autowired
    public UtilizerController(UtilizerService utilizerService) {
        this.utilizerService = utilizerService;
    }
    
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
    
    @RequestMapping("/login")
    public String login() {
        return "pages/login";
    }
    
    @GetMapping("/aboutUs")
    public String companyPage() {
        return "pages/aboutUs";
    }
    
    @GetMapping("/register")
    public String registration() {
        return "pages/registration";
    }
    
    @PostMapping("/addUser")
    public String createUtilizer(Model model,
                                 @RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("login") String login,
                                 @RequestParam("newpassword") String newpassword
    ) {
        utilizerService.createUtilizer(name, surname, login, newpassword);
        return "redirect:/";
    }
}
