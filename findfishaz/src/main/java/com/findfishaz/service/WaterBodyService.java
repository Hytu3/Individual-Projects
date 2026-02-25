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
          return "Fish species doesn't belong in any water body";
        }

        // Remove last comma
        sb.deleteCharAt(sb.length() - 1);

        return foundFish.getSpecies() + " is in these water bodies: " + sb.toString();


    }

    return "Fish doesn't exist in database";

    
 }

 @Transactional
 public Integer getWaterBodyIdByName(String name)
 {
    // Search for all water bodies by its name
    List<WaterBody> waterBodies = waterBodyRepository.findAll();

    for (WaterBody waterBody : waterBodies)
    {
      if (waterBody.getName().equals(name))
      {
        return waterBody.getId();
      }
    }

    // Water body id wasn't found
    return 0;
 }




}
