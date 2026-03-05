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
@RequestMapping("/fish")
public class FishController 
{
 
 // Dependency Injection
 private final FishService fishService;
 private final WaterBodyService waterBodyService;

 public FishController(FishService fishService, WaterBodyService waterBodyService)
 {
   this.fishService = fishService;
   this.waterBodyService = waterBodyService;
 }

 @GetMapping("/allFish") // URL: localhost:8080/fish/fishPage
 public String showFishPage(Model model)
 {
    model.addAttribute("fishList", fishService.showFishPage());

    return "fishPage";
  
 }
 
 // Leads user to empty form
 @GetMapping("/addFish") // URL: localhost:8080/fish/addFish
 public String addFishPage()
 {
  return "createFish";
 }
 
 @PostMapping("/create") // URL: localhost:8080/fish/create
 public String Create( Model model, @RequestParam String species, @RequestParam String info)
 {
  model.addAttribute("createMessage", fishService.create(species, info));
  model.addAttribute("fishSpecies", species);
  model.addAttribute("fishInfo", info);
  
  return "createdFish";
  
 }

 @ResponseBody
 @GetMapping("/read") // URL: localhost:8080/fish/read?id=1
 public String Read(Integer id)
 {
  String message = fishService.read(id);

  return message;
  
 }

 
 @GetMapping("/update") // URL: localhost:8080/fish/update
 public String Update()
 {
   return "updateFish";
 }
 
 
 @PostMapping("/updated") // URL: localhost:8080/fish/updated
 public String Update(Model model, @RequestParam String species, @RequestParam String info)
 {
  Integer id = fishService.getFishIdBySpecies(species);
  
  String update_message = fishService.update(id, species, info);

  model.addAttribute("updateMessage", update_message);

  return "updatedFish";
 }

 @GetMapping("/delete") // URL: localhost:8080/fish/delete
 public String Delete()
 {
   return "deleteFish";
 }
 
 
 @PostMapping("/deleted") // URL: localhost:8080/fish/deleted
 public String Delete(Model model, @RequestParam String species)
 {
   Integer id = fishService.getFishIdBySpecies(species);

   String delete_message = fishService.delete(id);

   model.addAttribute("deleteMessage", delete_message);

   return "deletedFish";
 }

  @GetMapping("/addFishToWaterBody")
  public String addFishToSpecificWaterBody()
  {
    return "addFishToAWaterBody";
  }
 
  @PostMapping("/addedFishToWaterBody")
  public String addFishToWaterBody(Model model, @RequestParam String species, @RequestParam String name)
  {
    Integer waterBodyId = waterBodyService.getWaterBodyIdByName(name);

    Integer fishId = fishService.getFishIdBySpecies(species);
    
    model.addAttribute("addMessage", fishService.addFishToWaterBody(waterBodyId, fishId));
    
    return "addedFishToAWaterBody";
  }

  @GetMapping("/deleteFishFromWaterBody")
  public String deleteFishFromSpecificWaterBody()
  {
    return "deleteFishFromAWaterBody";
  }
 
  @PostMapping("/deletedFishFromWaterBody")
  public String deletedFishFromWaterBody(Model model, @RequestParam String species, @RequestParam String name)
  {
    Integer waterBodyId = waterBodyService.getWaterBodyIdByName(name);

    Integer fishId = fishService.getFishIdBySpecies(species);
    
    model.addAttribute("deleteMessage", fishService.deleteFishFromWaterBody(waterBodyId, fishId));
    
    return "deletedFishFromAWaterBody";
  }

  
  @GetMapping("/searchFish")
  public String searchAllFishFromWaterBody()
  {
    return "searchAllFishFromCertainWaterBody";
  }
  
  
  @GetMapping("/findFishByWaterBody")
  public String findAllFishByWaterBody(Model model, @RequestParam String name)
  {
    Integer waterBodyId = waterBodyService.getWaterBodyIdByName(name);
    
    model.addAttribute("fishes", fishService.findAllFishByWaterBody(waterBodyId));
    
    return "fishFoundInSpecificWaterBody";
  }
}
