package com.findfishaz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

 public List<WaterBody> showWaterBodyPage()
 {
   return waterBodyRepository.findAll();
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

 @Transactional
 public String addFishToWaterBody(Integer waterBodyId, Integer fishId)
 {
   // Find water body to add fish to
   Optional<WaterBody> waterBody = waterBodyRepository.findById(waterBodyId);

   if (waterBody.isPresent())
   {
     WaterBody foundWaterBody = waterBody.get();
     
     List<Fish> list = foundWaterBody.getFishes();

     // Find specific fish
     Optional<Fish> fish = fishRepository.findById(fishId);

     
     // If its a valid fish species
     if (fish.isPresent())
     {
       Fish foundFish = fish.get();

       // Adds fish to waterbody
       list.add(foundFish);

     
       foundFish.setWaterBody(foundWaterBody);

       // Save changes
       waterBodyRepository.save(foundWaterBody);

       fishRepository.save(foundFish);

       return  foundFish.getSpecies() + " added to " + foundWaterBody.getName();
     }


   }

   return "Couldn't add fish to water body";


 }

 @Transactional
 public String findFishByWaterBody(Integer id)
 {
   // Find Specific Water Body in database
   Optional<WaterBody> waterBodyId = waterBodyRepository.findById(id);

   if (waterBodyId.isPresent())
   {
      // Build a string message of all fish into a waterbody
      StringBuilder sb = new StringBuilder();

      sb.append("List of fish species for ");
    
      WaterBody foundWaterBody = waterBodyId.get();

      String name = foundWaterBody.getName(); 
      
      sb.append(name + ": ");
      
      List<Fish> list = foundWaterBody.getFishes();

      for (Fish fish : list)
      {
        sb.append(fish.getSpecies() + ", ");
      }

      // Delete last comma
      sb.deleteCharAt(sb.length() - 2);

      return sb.toString();
   }

   return "Couldn't find water body in database";

 }

 @Transactional
 public String findWaterBodiesWithFish (Integer fishId)
 {
    // Get a list of all water bodies
    List<WaterBody> waterBodyList = waterBodyRepository.findAll();

    // Find Specific fish in database
    Optional<Fish> fish = fishRepository.findById(fishId);

    // If fish exists
    if (fish.isPresent())
    {
        Fish foundFish = fish.get();

        StringBuilder sb = new StringBuilder();
        
        // See which waterbodies contain targeted fish and return them
        for (WaterBody waterBody : waterBodyList)
        {
          List<Fish> list = waterBody.getFishes();

          if (list.contains(foundFish))
          {
            sb.append(waterBody.getName());
            sb.append(" ,");
          }
        }
        
        // If no waterbody contains this fish
        if (sb.isEmpty())
        {
          return "Fish species doesny belong in any lake";
        }

        // Remove last comma
        sb.deleteCharAt(sb.length() - 1);

        return foundFish.getSpecies() + " is in these waterbodies: " + sb.toString();


    }

    return "Fish doesnt exist in database";

    
 }




}
