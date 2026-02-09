package com.findfishaz.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.findfishaz.model.Fish;
import com.findfishaz.service.WaterBodyService;

@Controller
@RequestMapping("/waterbody")
public class WaterBodyController 
{
  // Dependency Injection
  private final WaterBodyService waterBodyService;

  public WaterBodyController(WaterBodyService waterBodyService)
  {
    this.waterBodyService = waterBodyService;
  }

  @ResponseBody
  @PostMapping("/create")
  public String Create(String name, String city, String type, Boolean isPrivate)
  {
    String message = waterBodyService.create(name, city, type, isPrivate);

    return message;
  }

  @ResponseBody
  @GetMapping("/read")
  public String Read(Integer id)
  {
    String message = waterBodyService.read(id);

    return message;
  }

  @ResponseBody
  @PostMapping("/update")
  public String Update(Integer id, String name, String city, String type, Boolean isPrivate)
  {
    String message = waterBodyService.update(id, name, city, type, isPrivate);

    return message;
  }

  @ResponseBody
  @DeleteMapping("/delete")
  public String Delete(Integer id)
  {
    String message = waterBodyService.delete(id);

    return message;
  }

  @ResponseBody
  @PostMapping("/addFishToWaterBody")
  public String addFishToWaterBody(Integer id, Fish fish)
  {
    String message = waterBodyService.addFishToWaterBody(id,fish);
    
    return message;
  }




}
