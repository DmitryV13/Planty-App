package com.planty_app.Planty.services;


import com.planty_app.Planty.models.Credentials;
import com.planty_app.Planty.models.Utilizer;


import com.planty_app.Planty.repositories.UtilizerRepository;
import com.planty_app.Planty.services.MyPlantSampleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UtilizerService {
    private final UtilizerRepository utilizerRepository;
    private final MyPlantSampleService myPlantSampleService;
    private final PasswordEncoder passwordEncoder;
    UtilizerService(UtilizerRepository utilizerRepository,
                    MyPlantSampleService myPlantSampleService,
                    PasswordEncoder passwordEncoder) {
        this.utilizerRepository = utilizerRepository;
        this.myPlantSampleService = myPlantSampleService;
        this.passwordEncoder = passwordEncoder;
    }
    
    //works without dublicates
    public void createUtilizer(String name, String surname, String login, String password) {
        Credentials credentials=new Credentials()
                .withPassword(passwordEncoder.encode(password));
        Utilizer newUtilizer = new Utilizer()
                .withName(name)
                .withSurname(surname)
                .withLogin(login)
                .withCredentials(credentials);
        utilizerRepository.save(newUtilizer);
    }
    
    public void createUtilizer(Utilizer newUtilizer, String password, MultipartFile image) throws IOException {
        Credentials credentials=new Credentials()
                .withPassword(passwordEncoder.encode(password));
        try {
            newUtilizer.setAvatar(image.getBytes());
        }catch (IOException e){
            throw new IOException("Something wrong with photo");
        }
        newUtilizer.setBase64Avatar(newUtilizer.getAvatar());
        newUtilizer.setCredentials(credentials);
        utilizerRepository.save(newUtilizer);
    }
    
    public Utilizer findUtilizerByLogin(String login){
        return utilizerRepository.findUtilizerByLogin(login).get();
    }
    
    public Utilizer findUtilizerById(Long id){
        return utilizerRepository.findUtilizerById(id).get();
    }
    
    public List<Utilizer> findAllUsers(){
        return utilizerRepository.findAll();
    }
    
    public void deleteUserById(Long id){
        Utilizer userToDelete=utilizerRepository.findUtilizerById(id).get();
        myPlantSampleService.deletePlantSamplesFromUser(userToDelete);
        utilizerRepository.delete(userToDelete);
    }
    
    public void setNewAvatar(Utilizer user, MultipartFile newAvatar) throws IOException{
        try {
            user.setAvatar(newAvatar.getBytes());
        }catch (IOException e){
            throw new IOException("Something wrong with photo");
        }
        user.setBase64Avatar(user.getAvatar());
        utilizerRepository.save(user);
    }
}
