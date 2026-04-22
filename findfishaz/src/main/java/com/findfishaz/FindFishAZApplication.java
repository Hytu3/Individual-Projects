package com.findfishaz;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.findfishaz.service.FishTest;

/**
 * The main entry point for the FindFishAZ application.
 */
@SpringBootApplication
public class FindFishAZApplication 
{

    public static void main(String[] args) 
    {
        try 
        {
        SpringApplication.run(FindFishAZApplication.class, args);
        } catch (Exception e) {
            System.err.println("\n--- !!! REAL ERROR DETECTED !!! ---");
            e.printStackTrace(); // This prints the EXACT line number and error type
            System.err.println("-----------------------------------\n");
        }
    }

    @Bean
    public CommandLineRunner runAutomatedTests(FishTest fishTest) 
    {
        return args -> {
            System.out.println("--- STARTING AUTOMATED DEMO TESTS ---");

            // 1. Run Functional Create Fish Test
            String createResult = fishTest.testCreate("Goldfish", "Standard Pet Fish", "Fish Created");
            System.out.println("FUNCTIONAL TEST - Create: " + createResult);

            // 2. Run Non-Functional Query Stress Test
            int iterations = 50; // Set it here
            double queryTime = fishTest.getQueryTime("Largemouth Bass", "Salt River", iterations);
            System.out.println("NON-FUNCTIONAL TEST - " + iterations + " Queries took: " + queryTime + " seconds");

            // 1. Run Functional Delete Fish Test
            String deleteResult = fishTest.testDelete("Goldfish", "Fish deleted from database");
            System.out.println("FUNCTIONAL TEST - Create: " + deleteResult);
            
            System.out.println("--- TESTS COMPLETED ---");
        };
    }

}