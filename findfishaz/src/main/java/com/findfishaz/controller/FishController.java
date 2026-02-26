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

 @ResponseBody
 @PostMapping("/update") // URL: localhost:8080/fish/update
 public String Update(Integer id, String species, String info)
 {
   String message = fishService.update(id, species, info);

   return message;
 }

 @ResponseBody
 @DeleteMapping("/delete") // URL: localhost:8080/fish/delete
 public String Delete(Integer id)
 {
   String message = fishService.delete(id);

   return message;
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
