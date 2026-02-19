package com.findfishaz;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.findfishaz.service.FishService;
import com.findfishaz.service.WaterBodyService;

/**
 * The main entry point for the FindFishAZ application.
 */
@SpringBootApplication
public class FindFishAZApplication 
{

    public static void main(String[] args) 
    {
        // This single line launches the entire Spring Boot framework
        SpringApplication.run(FindFishAZApplication.class, args);
    }

    // AI generated test code
    @Bean
    public CommandLineRunner demo(FishService fishService, WaterBodyService waterBodyService) 
    {
        return args -> 
        {
            System.out.println("--- STARTING CRUD TEST ---");

            /*
            // 1. TEST CREATE
            String createMsg = waterBodyService.create("Lake Pleasant", "Peoria", "Lake", false);
            System.out.println("Create Result: " + createMsg);

            // 2. TEST READ
            // Assuming this is the first item (ID 1)
            String readMsg = waterBodyService.read(1);
            System.out.println("Read Result: " + readMsg);

            // 3. TEST UPDATE
            String updateMsg = waterBodyService.update(1, "Lake Pleasant", "Peoria", "Large Lake", false);
            System.out.println("Update Result: " + updateMsg);

            // 4. TEST FISH RELATIONSHIP
            // Let's create a fish and link it to WaterBody ID 1
            // (Using the method we discussed for FishService)
            String linkMsg = fishService.create("Largemouth Bass", "Loves shady spots");
            System.out.println("Fish Create: " + linkMsg);
            
            */
            //5. TEST DELETE
            String deleteMsg = fishService.delete(2);
            System.out.println(deleteMsg);

            // 6. TEST ADD FISH TO WATER BODY
            String addMessage = waterBodyService.addFishToWaterBody(1,1);
            System.out.println(addMessage);

            // 7. TEST SEARCH FISH BY WATER BODY
            String searchMessage = waterBodyService.findFishByWaterBody(1);
            System.out.println(searchMessage);

            System.out.println("--- CRUD TEST FINISHED ---");
        };
    }
}