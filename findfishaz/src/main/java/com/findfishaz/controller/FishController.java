package com.findfishaz.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.findfishaz.service.FishService;

@Controller
@RequestMapping("/fish")
public class FishController 
{
 
 // Dependency Injection
 private final FishService fishService;

 public FishController(FishService fishService)
 {
   this.fishService = fishService;
 }

 @ResponseBody
 @PostMapping("/create") // URL: localhost:8080/fish/create
 public String Create(String species, String info)
 {
  String message = fishService.create(species, info);

  return message;
  
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
}
