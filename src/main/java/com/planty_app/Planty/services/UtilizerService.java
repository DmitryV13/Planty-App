package com.planty_app.Planty.services;

import com.planty_app.Planty.models.Credentials;
import com.planty_app.Planty.models.Utilizer;
import com.planty_app.Planty.repositories.UtilizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilizerService {
    private final UtilizerRepository utilizerRepository;
    
    @Autowired
    UtilizerService(UtilizerRepository utilizerRepository) {
        this.utilizerRepository = utilizerRepository;
    }
    
    //works without dublicates
    public void createUtilizer(String name, String surname, String login, String password) {
        Credentials credentials=new Credentials()
                .withPassword(password);
        Utilizer newUtilizer = new Utilizer()
                .withName(name)
                .withSurname(surname)
                .withLogin(login)
                .withCredentials(credentials);
        utilizerRepository.save(newUtilizer);
    }
}
