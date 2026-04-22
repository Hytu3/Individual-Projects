package com.findfishaz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Component
public class FishTest{
 
  private final FishService fishService;
  private final WaterBodyService waterBodyService;

  // Dependency Injection
  @Autowired
  public FishTest(FishService fishService, WaterBodyService waterBodyService)
  {
    this.fishService = fishService;
    this.waterBodyService = waterBodyService;
  }

  // Functional tests for CRUD

  // We will be testing these with a placeholder fish called goldfish

  public String testCreate(String fish, String info, String expected)
  {
    String result = fishService.create(fish,info);


    if (result.equals(expected))
    {
      return "Passed";
    }

    return "Failed";
  }


  public String testRead(String fish, String expected)
  {
    Integer id = fishService.getFishIdBySpecies(fish);

    String result = fishService.getFishInfo(id);

    if (result.equals(expected))
    {
      return "Passed";
    }

    return "Failed";

  }

  
  public String testUpdate(String fish, String info, String expected)
  {
    Integer id = fishService.getFishIdBySpecies(fish);

    String result = fishService.update(id, fish, info);

    if (result.equals(expected))
    {
      return "Passed";
    }

    return "Failed";
  }

  
  public String testDelete(String fish, String expected)
  {
    Integer id = fishService.getFishIdBySpecies(fish);

    String result = fishService.delete(id);

    if (result.equals(expected))
    {
      return "Passed";
    }

    return "Failed";
  }

  // Non functional tests for queries. Get its time performance
  
  // Stress test x different queries at once
  
  public double getQueryTime(String fish, String waterBody,int x)
  {
    
    StopWatch stopWatch = new StopWatch();

    Integer id = fishService.getFishIdBySpecies(fish);

    stopWatch.start();
    while (x > 0)
    {
      waterBodyService.findWaterBodiesWithFish(id);
      x--;
    }
    
    stopWatch.stop();

    return stopWatch.getTotalTimeSeconds();
  }


}