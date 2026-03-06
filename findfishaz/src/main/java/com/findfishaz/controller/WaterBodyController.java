package com.findfishaz.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.findfishaz.service.FishService;
import com.findfishaz.service.WaterBodyService;

@Controller
@RequestMapping("/waterBody")
public class WaterBodyController 
{
  // Dependency Injection
  private final WaterBodyService waterBodyService;
  private final FishService fishService;

  public WaterBodyController(WaterBodyService waterBodyService, FishService fishService)
  {
    this.waterBodyService = waterBodyService;
    this.fishService = fishService;
  }

  @GetMapping("/allWaterBodies") 
  public String showWaterBodyPage(Model model)
  {
      model.addAttribute("waterBodyList", waterBodyService.showWaterBodyPage());

      return "waterBodyPage";
  }
  
  // Leads user to empty form
  @GetMapping("/addWaterBody")
  public String addWaterBodyPage()
  {
    return "createWaterBody";
  }
  
  @PostMapping("/create")
  public String Create(Model model, @RequestParam String name, @RequestParam String city, @RequestParam String type, @RequestParam Boolean isPrivate)
  {
    model.addAttribute("createMessage", waterBodyService.create(name,city,type,isPrivate));
    model.addAttribute("waterBodyName", name);
    model.addAttribute("waterBodyCity", city);
    model.addAttribute("waterBodyType", type);
    model.addAttribute("isBodyPrivate", isPrivate);

    return "createdWaterBody";
  }

  @ResponseBody
  @GetMapping("/read")
  public String Read(Integer id)
  {
    String message = waterBodyService.read(id);

    return message;
  }

  @GetMapping("/update") // URL: localhost:8080/waterBody/update
  public String Update()
  {
    return "updateWaterBody";
  }
  
  
  @PostMapping("/updated")  // URL: localhost:8080/waterBody/update
  public String Update(Model model, @RequestParam String name, @RequestParam(required = false) String city, @RequestParam(required = false) String type, @RequestParam(required = false) Boolean isPrivate)
  {
    Integer id = waterBodyService.getWaterBodyIdByName(name);
    
    String updateMessage = waterBodyService.update(id, name, city, type, isPrivate);

    model.addAttribute("update_message", updateMessage);

    return "updatedWaterBody";
  }

  
  @GetMapping("/delete")
  public String Delete()
  {

    return "deleteWaterBody";

  }
  
  
  @PostMapping("/deleted")
  public String Delete(Model model, @RequestParam String name)
  {
    Integer id = waterBodyService.getWaterBodyIdByName(name);
    
    String delete_message = waterBodyService.delete(id);

    model.addAttribute("deleteMessage", delete_message);

    return "deletedWaterBody";

  }

  @GetMapping("/searchWaterBody")
  public String searchWaterBodies()
  {
    return "searchWaterBodiesWithSpecificFish";
  }
  
  @GetMapping("/findWaterBodiesWithSpecificFish")
  public String findWaterBodiesWithFish (Model model, @RequestParam String species)
  {
    // Get ID to use into parameters
    Integer fishId = fishService.getFishIdBySpecies(species);
    
    String waterBodies = waterBodyService.findWaterBodiesWithFish(fishId);

    model.addAttribute("waterBodies_List", waterBodies);

    return "waterBodiesFoundWithSpecificFish";
  }

}
