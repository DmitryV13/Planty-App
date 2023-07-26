package com.planty_app.Planty.initializer;

import com.planty_app.Planty.models.*;
import com.planty_app.Planty.repositories.MyPlantSampleRepository;
import com.planty_app.Planty.repositories.PlantRepository;
import com.planty_app.Planty.repositories.UtilizerRepository;
import com.planty_app.Planty.services.MyPlantSampleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Period;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UtilizerRepository utilizerRepository;
    private final PlantRepository plantRepository;
    private final MyPlantSampleRepository myPlantSampleRepository;
    private final MyPlantSampleService myPlantSampleService;
    private final PasswordEncoder passwordEncoder;
    DataInitializer(UtilizerRepository utilizerRepository,
                    PlantRepository plantRepository,
                    MyPlantSampleRepository myPlantSampleRepository,
                    MyPlantSampleService myPlantSampleService,
                    PasswordEncoder passwordEncoder){
        this.utilizerRepository=utilizerRepository;
        this.plantRepository=plantRepository;
        this.myPlantSampleRepository=myPlantSampleRepository;
        this.myPlantSampleService = myPlantSampleService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        //PLANTS
        //1
        History history1=new History()
                .withFamily("Asteraceae")
                .withSpecies("Matricaria");
        Description description1=new Description()
                .withExternal("Roman chamomile (Matricaria chamomilla), also known as German chamomile or true chamomile, is a small, daisy-like flower with a bright yellow center and delicate white petals. It has a gentle, pleasant fragrance and is commonly recognized for its soothing and calming properties. The flower is characterized by its low-growing habit and feathery, fern-like leaves.")
                .withFragmentation("The daisy reproduces through a process called seed propagation, where mature flowers produce seeds that can develop into new plants. These seeds are dispersed by various means such as wind, animals, or human intervention. Once the seeds find suitable conditions, they germinate and grow into new daisy plants, continuing the life cycle of this beautiful flower.")
                .withProtectionStatus(ProtectionStatus.USUAL);
        Watering watering1=new Watering()
                .withAmountOfWaterKey(new int[]{0,10,60,105})
                .withAmountOfWaterValue(new int[]{70,150,70})
                .withPeriod(Period.ofDays(2));//5
        Conditions conditions1=new Conditions()
                .withTemperature("15-25 degrees Celsius")
                .withHumidity("Chamomile thrives in moderate humidity. The optimal humidity level for chamomile growth is approximately 50-60%. However, the plant is able to survive in higher or lower humidity levels.")
                .withLighting("Chamomile usually thrives in bright light, preferring full sun or light lighting. Optimal lighting for chamomile includes 6-8 hours of direct sunlight per day. However, it can also survive in some shade.")
                .withSoil("Chamomile prefers well-drained soil with a neutral to slightly alkaline pH. It can grow in a variety of soil types, including garden soil, sandy soils, and loamy soils.")
                .withFertilizer("For chamomile it is recommended to use: Organic fertilizers such as compost or humus Biostimulants to boost immunity and increase disease resistance Balanced mineral fertilizers for flowering plants or herbaceous plants.")
                .withTransplantation("A chamomile transplant may be needed if the plant has grown and become overcrowded in the current pot, or if the soil in it has become depleted.")
                .withThreats("Threats to chamomile: lack of light, waterlogging or lack of moisture, poor soil, diseases and pests.")
                .withWatering(watering1);
        
        String folder="D:\\Applications\\Demo\\Planty-App\\src\\main\\resources\\static\\images\\";
        BufferedImage bImage = ImageIO.read(new File(folder,"chamomile.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        
        Plant plant1=new Plant()
                .withName("Bellis perennis")
                .withPhoto(data)
                .withHistory(history1)
                .withDescription(description1)
                .withConditions(conditions1);
        plant1.setBase64Photo(data);
        
        plantRepository.save(plant1);
        //PLANTS
        
        //ADMINISTRATOR
        Credentials adminCreds=new Credentials()
                .withPassword(passwordEncoder.encode("password"));
        
        BufferedImage bImage2 = ImageIO.read(new File(folder,"cat.jpg"));
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ImageIO.write(bImage2, "jpg", bos2 );
        byte [] data2 = bos2.toByteArray();
        
        Utilizer administrator=new Utilizer()
                .withName("admin")
                .withSurname("admin")
                .withLogin("admin")
                .withAvatar(data2)
                .withRole(Role.ADMIN)
                .withCredentials(adminCreds);
        administrator.setBase64Avatar(data2);
        
        utilizerRepository.save(administrator);
        //ADMINISTRATOR
        
        //SIMPLE USER
        Credentials userCreds=new Credentials()
                .withPassword(passwordEncoder.encode("pas"));
        
        Utilizer simpleUser=new Utilizer()
                .withName("simpleUser")
                .withSurname("simpleUser")
                .withLogin("user")
                .withCredentials(userCreds);
        
        utilizerRepository.save(simpleUser);
        //SIMPLE USER
        
        //MODERATOR
        Credentials modCreds=new Credentials()
                .withPassword(passwordEncoder.encode("pass"));
        
        BufferedImage bImage3 = ImageIO.read(new File(folder,"owl.jpg"));
        ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
        ImageIO.write(bImage3, "jpg", bos3 );
        byte [] data3 = bos3.toByteArray();
        
        Utilizer mod=new Utilizer()
                .withName("moderator")
                .withSurname("moderator")
                .withLogin("mod")
                .withAvatar(data3)
                .withRole(Role.MODERATOR)
                .withCredentials(modCreds);
        mod.setBase64Avatar(data3);
        
        utilizerRepository.save(mod);
        //MODERATOR
        
        //MY SAMPLES
        myPlantSampleService.createNewPlantSample(simpleUser,plant1,"1");
        myPlantSampleService.createNewPlantSample(simpleUser,plant1,"2");
//        MyPlantSample mySample1=new MyPlantSample()
//                .withPlant(plant1)
//                .withPlantAge(3)
//                .withUtilizer(simpleUser);
//        MyPlantSample mySample2= new MyPlantSample()
//                .withPlant(plant1)
//                .withPlantAge(3)
//                .withUtilizer(simpleUser);
//        myPlantSampleRepository.save(mySample1);
//        myPlantSampleRepository.save(mySample2);
        //MY SAMPLES
    }
    
}
