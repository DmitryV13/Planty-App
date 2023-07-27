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
                .withPeriod(Period.ofDays(5));
        Conditions conditions1=new Conditions()
                .withTemperature("15-25 degrees Celsius")
                .withHumidity("Chamomile thrives in moderate humidity. The optimal humidity level for chamomile growth is approximately 50-60%. However, the plant is able to survive in higher or lower humidity levels.")
                .withLighting("Chamomile usually thrives in bright light, preferring full sun or light lighting. Optimal lighting for chamomile includes 6-8 hours of direct sunlight per day. However, it can also survive in some shade.")
                .withSoil("Chamomile prefers well-drained soil with a neutral to slightly alkaline pH. It can grow in a variety of soil types, including garden soil, sandy soils, and loamy soils.")
                .withFertilizer("For chamomile it is recommended to use: Organic fertilizers such as compost or humus Biostimulants to boost immunity and increase disease resistance Balanced mineral fertilizers for flowering plants or herbaceous plants.")
                .withTransplantation("A chamomile transplant may be needed if the plant has grown and become overcrowded in the current pot, or if the soil in it has become depleted.")
                .withThreats("Threats to chamomile: lack of light, waterlogging or lack of moisture, poor soil, diseases and pests.")
                .withWatering(watering1);
        
        String folder = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\images\\";
        
        BufferedImage bImage = ImageIO.read(new File(folder,"chamomile.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] chamomile = bos.toByteArray();
        
        Plant plant1=new Plant()
                .withName("Bellis perennis")
                .withPhoto(chamomile)
                .withHistory(history1)
                .withDescription(description1)
                .withConditions(conditions1);
        plant1.setBase64Photo(chamomile);
        
        plantRepository.save(plant1);
        
        //2
        History history2=new History()
                .withFamily("Liliaceae")
                .withSpecies("Lilium");
        Description description2=new Description()
                .withExternal("The white lily has large, trumpet-shaped flowers with six petal-like tepals. The petals are pure white, and the flower has a striking golden-yellow stamen in the center. The plant has long, lance-shaped green leaves that are arranged alternately along the stem. The white lily is known for its elegant and graceful appearance, making it a popular choice in gardens and floral arrangements.")
                .withFragmentation("White lilies can be propagated through seeds, bulb division, and offsets. Seeds can be collected after pollination and sown in a suitable medium. Bulbs can be divided into smaller sections and replanted. Some lilies produce offsets, which can be separated and grown into new plants. Tissue culture is another method used for large-scale propagation or cloning.")
                .withProtectionStatus(ProtectionStatus.USUAL);
        Watering watering2=new Watering()
                .withAmountOfWaterKey(new int[]{0,30,60,90,120,150})
                .withAmountOfWaterValue(new int[]{70,120,170,230,270})
                .withPeriod(Period.ofDays(10));
        Conditions conditions2=new Conditions()
                .withTemperature("18-25 degrees Celsius")
                .withHumidity("The white lily thrives in an environment with a relative humidity between 40% and 60%, which provides optimal conditions for its growth and development. Maintaining proper humidity levels is essential for ensuring the healthy and lush growth of this beautiful flowering plant.")
                .withLighting("The white lily prefers a bright, well-lit location with indirect sunlight. It thrives in moderate to high light conditions, making it essential to place the plant in a spot that receives adequate illumination throughout the day.")
                .withSoil("The white lily thrives in well-draining, fertile soil that is rich in organic matter. It prefers slightly acidic to neutral soil pH levels and requires good aeration to support healthy root growth.")
                .withFertilizer("Fertilizers rich in potassium, phosphorus, and nitrogen are beneficial for the growth of white lilies. These nutrients support strong root development, promote flower production, and enhance overall plant vigor. Additionally, organic fertilizers like compost or well-rotted manure can also provide essential nutrients and improve soil structure, aiding in the successful growth of the white lily.")
                .withTransplantation("To ensure favorable growth of white lilies, it is recommended to transplant them during their dormant period, typically in late autumn or early spring. Gently dig up the lily bulbs, being careful not to damage the roots, and replant them in a well-draining soil enriched with organic matter. Choose a sunny or partially shaded location with good air circulation for optimal transplant success. Water the newly transplanted lilies adequately and monitor their progress to encourage healthy growth")
                .withThreats("Threats to the favorable growth of white lilies include pests, diseases, environmental stress, competition with other plants, and human activities like habitat destruction and pollution. Invasive species can also pose a risk by disrupting the ecosystem. Proper care and conservation efforts are crucial to support their growth.")
                .withWatering(watering2);
        
        bImage = ImageIO.read(new File(folder,"lily.jpg"));
        bos.reset();
        ImageIO.write(bImage, "jpg", bos );
        byte [] lily = bos.toByteArray();
        
        Plant plant2=new Plant()
                .withName("Lilium candidum")
                .withPhoto(lily)
                .withHistory(history2)
                .withDescription(description2)
                .withConditions(conditions2);
        plant2.setBase64Photo(lily);
        
        plantRepository.save(plant2);
        
        //3
        History history3=new History()
                .withFamily("Asphodelaceae")
                .withSpecies("Asphodelaceae");
        Description description3=new Description()
                .withExternal("Aloe plants have thick, succulent leaves arranged in a rosette formation. The leaves are lance-shaped with serrated edges and a smooth surface, typically displaying a green or bluish-green hue. Some species of aloe may have white markings on the leaves, and the plant can produce tall flower spikes with tubular, vibrant-colored flowers, enhancing its visual appeal as an ornamental plant both indoors and outdoors.")
                .withFragmentation("Aloe plants can be propagated through various methods, including division, stem cuttings, and seeds. Division involves separating offsets or \"pups\" that grow at the base of mature plants and replanting them. Stem cuttings can be taken from healthy leaves, left to dry, and then planted in well-draining soil until roots develop. Additionally, aloe plants produce seeds that can be collected, sown in suitable soil, and kept in a warm environment to encourage germination.")
                .withProtectionStatus(ProtectionStatus.USUAL);
        Watering watering3=new Watering()
                .withAmountOfWaterKey(new int[]{0,21,80,780})
                .withAmountOfWaterValue(new int[]{50,80,130})
                .withPeriod(Period.ofDays(8));
        Conditions conditions3=new Conditions()
                .withTemperature("20-230 degrees Celsius")
                .withHumidity("The approximate favorable humidity for the growth of aloe is around 40% to 50%.")
                .withLighting("Aloe thrives best in bright, indirect sunlight or partial shade, making it well-suited for indoor environments or areas with filtered light. Direct, intense sunlight should be avoided as it can lead to sunburn and damage the plant.")
                .withSoil("Aloe prefers well-draining, sandy or loamy soil with a slightly acidic to neutral pH level. The soil should have good aeration and allow excess water to flow out easily, preventing waterlogging and root rot.")
                .withFertilizer("For Aloe growth, use a balanced fertilizer with a higher phosphorus content, applied during the growing season (spring and summer). Additionally, a diluted liquid fertilizer containing essential nutrients can be applied monthly to promote healthy development.")
                .withTransplantation("For optimal Aloe growth, repot the plant every 2-3 years during the spring season. Use a well-draining potting mix, preferably a cactus or succulent mix, and ensure the new pot is slightly larger to accommodate the growing root system. Handle the plant with care during transplanting to avoid damaging the roots.")
                .withThreats("Some potential threats to the optimal growth of Aloe include overwatering, which can lead to root rot, and underwatering, causing dehydration. Prolonged exposure to frost or extremely low temperatures can also harm the plant. Additionally, pests like mealybugs and aphids may infest Aloe, affecting its health and growth.")
                .withWatering(watering3);
        
        bImage = ImageIO.read(new File(folder,"aloe.jpg"));
        bos.reset();
        ImageIO.write(bImage, "jpg", bos );
        byte [] aloe = bos.toByteArray();
        
        Plant plant3=new Plant()
                .withName("Aloe vera")
                .withPhoto(aloe)
                .withHistory(history3)
                .withDescription(description3)
                .withConditions(conditions3);
        plant3.setBase64Photo(aloe);
        
        plantRepository.save(plant3);
        //PLANTS
        
        //ADMINISTRATOR
        Credentials adminCreds=new Credentials()
                .withPassword(passwordEncoder.encode("password"));
        

        bImage = ImageIO.read(new File(folder,"cat.jpg"));
        bos.reset();
        ImageIO.write(bImage, "jpg", bos );
        byte [] adminAvatar = bos.toByteArray();
        
        Utilizer administrator=new Utilizer()
                .withName("admin")
                .withSurname("admin")
                .withLogin("admin")
                .withAvatar(adminAvatar)
                .withRole(Role.ADMIN)
                .withCredentials(adminCreds);
        administrator.setBase64Avatar(adminAvatar);
        
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
        
        bImage = ImageIO.read(new File(folder,"owl.jpg"));
        bos.reset();
        ImageIO.write(bImage, "jpg", bos );
        byte [] modAvatar = bos.toByteArray();
        
        Utilizer mod=new Utilizer()
                .withName("moderator")
                .withSurname("moderator")
                .withLogin("mod")
                .withAvatar(modAvatar)
                .withRole(Role.MODERATOR)
                .withCredentials(modCreds);
        mod.setBase64Avatar(modAvatar);
        
        utilizerRepository.save(mod);
        //MODERATOR
        
        //MY SAMPLES
        myPlantSampleService.createNewPlantSample(simpleUser,plant1,"1");
        myPlantSampleService.createNewPlantSample(simpleUser,plant1,"2");
        //MY SAMPLES
    }
    
}
