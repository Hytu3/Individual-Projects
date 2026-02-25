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

}