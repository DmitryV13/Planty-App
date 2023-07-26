package com.planty_app.Planty.controllers;

import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.services.UtilizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

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
    
    @GetMapping("/changeAvatar")
    public String changeAvatar(){
        return "pages/changeAvatar";
    }
    
    @PostMapping("/changeCurrentAvatar")
    public String changeCurrentAvatar(Principal principal,
                                      Model model,
                                      @RequestParam("file") MultipartFile newAvatar){
        Utilizer currentUtilizer=utilizerService.findUtilizerByLogin(principal.getName());
        try {
            utilizerService.setNewAvatar(currentUtilizer, newAvatar);
        }catch (IOException e){
            model.addAttribute("fileError", e.getMessage());
        }
        return "redirect:/myGarden";
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
    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        utilizerService.deleteUserById(userId);
        return "redirect:/viewAllUsers";
    }
}
