package com.findfishaz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.findfishaz.model.Fish;
import com.findfishaz.model.WaterBody;
import com.findfishaz.repository.FishRepository;
import com.findfishaz.repository.WaterBodyRepository;


@Service
public class WaterBodyService 
{

 private final WaterBodyRepository waterBodyRepository;
 private final FishRepository fishRepository;

 public WaterBodyService(WaterBodyRepository waterBodyRepository, FishRepository fishRepository)
 {
   this.waterBodyRepository = waterBodyRepository;
   this.fishRepository = fishRepository;
 }

 public String create(String name, String city, String type, Boolean isPrivate)
 {
    // Create new waterbody object
    WaterBody waterBody = new WaterBody();

    // Set waterbody fields
    waterBody.setName(name);

    waterBody.setCity(city);

    waterBody.setType(type);

    waterBody.setIsPrivate(isPrivate);
  
    // Add to database
    waterBodyRepository.save(waterBody);

    return "Success";
 }

 public String read(Integer id)
 {
   // Find water body to make sure it exists
   Optional<WaterBody> waterBodyId = waterBodyRepository.findById(id);

   if (waterBodyId.isPresent())
   {
      WaterBody foundWaterBody = waterBodyId.get();

      Boolean isPrivateBody = foundWaterBody.getIsPrivate();
    
      if (isPrivateBody)
      {
        return "Water body name: " + foundWaterBody.getName() + " Water body location: " + foundWaterBody.getCity() + " Water body type: " + foundWaterBody.getType() + " WaterBody is private";
      }
      else
      {
        return "Water body name: " + foundWaterBody.getName() + " Water body location: " + foundWaterBody.getCity() + " Water body type: " + foundWaterBody.getType();
      }
   }

   return "Failure due to water body not being in database";
 }

 public String update(Integer id, String name, String city, String type, Boolean isPrivate)
 {
   // Find water body to make sure it exists
   Optional<WaterBody> waterBodyId = waterBodyRepository.findById(id);

   if (waterBodyId.isPresent())
   {

     WaterBody foundWaterBody = waterBodyId.get();
     
     // Change fields
     foundWaterBody.setName(name);

     foundWaterBody.setCity(city);

     foundWaterBody.setType(type);

     foundWaterBody.setIsPrivate(isPrivate);

     waterBodyRepository.save(foundWaterBody);

     return "Update successfully";

   }

   return "Failure to update as water body is not in database";

 }

 public String delete(Integer id)
 {

   // Find water body to make sure it exists
   Boolean ifExists = waterBodyRepository.existsById(id);

   if (ifExists)
   {
     waterBodyRepository.deleteById(id);

     return "Water body deleted from database";
   }

   return "Water body couldn't be deleted due to it not being in database";

 }

 public String addFishToWaterBody(Integer id, Fish fish)
 {
   // Find water body to add fish to
   Optional<WaterBody> waterBodyId = waterBodyRepository.findById(id);

   if (waterBodyId.isPresent())
   {
     WaterBody foundWaterBody = waterBodyId.get();
     
     List<Fish> list = foundWaterBody.getFishes();

     // Find specific fish
     Optional<Fish> fishId = fishRepository.findById(fish.getId());

     
     if (fishId.isPresent())
     {
       Fish foundFish = fishId.get();

       // Adds fish to waterbody
       list.add(foundFish);

     
       foundFish.setWaterBody(foundWaterBody);

       // Save changes
       waterBodyRepository.save(foundWaterBody);

       fishRepository.save(foundFish);

       return "Fish added to water body";
     }


   }

   return "Couldn't add fish to water body";


 }





}
