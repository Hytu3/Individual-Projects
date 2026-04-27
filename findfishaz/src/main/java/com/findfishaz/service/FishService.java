package com.findfishaz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.findfishaz.model.Fish;
import com.findfishaz.model.WaterBody;
import com.findfishaz.repository.FishRepository;
import com.findfishaz.repository.WaterBodyRepository;

@Service
public class FishService 
{
 
 // Inject Repository

 private final FishRepository fishRepository;
 private final WaterBodyRepository waterBodyRepository;

 @Autowired
 public FishService(FishRepository fishRepository, WaterBodyRepository waterBodyRepository) 
 {
     this.fishRepository = fishRepository;
     this.waterBodyRepository = waterBodyRepository;
 }

 public List<Fish> showFishPage()
 {
   return fishRepository.findAll();
 }
 
 public String create(String species, String info)
 {
    // Prevent duplicate creations
    // See if fish already exists in the database
    Integer fishId = getFishIdBySpecies(species);

    Optional<Fish> foundFish = fishRepository.findById(fishId);

    if (foundFish.isPresent())
    {
        return "Fish already exists in the database";     
    }
      
    // Create new fish object
    Fish fish = new Fish();

    // Set fish fields
    fish.setSpecies(species);

    fish.setInfo(info);
  
    // Add to database
    fishRepository.save(fish);

    return "Fish Created";
 }

 public String read(Integer id)
 {
   // Find fish to make sure it exists
   Optional<Fish> fishId = fishRepository.findById(id);

   if (fishId.isPresent())
   {
      Fish foundFish = fishId.get();
    
      return "Fish species: " + foundFish.getSpecies() + " Fish info: " + foundFish.getInfo();
   }

   return "Failure due to fish not being in database";
 }

 public String update(Integer id, String species, String info)
 {
   // Find fish to make sure it exists
   Optional<Fish> fishId = fishRepository.findById(id);

   if (fishId.isPresent())
   {

     Fish foundFish = fishId.get();
    
     // Update info
     foundFish.setInfo(info);

     fishRepository.save(foundFish);

     return "Updated info successfully";

   }

   return "Failure to update as fish is not in database";

 }

 public String delete(Integer id)
 {

   // Find fish to make sure it exists
   Boolean ifExists = fishRepository.existsById(id);

   if (ifExists)
   {
     fishRepository.deleteById(id);

     return "Fish deleted from database";
   }

   return "Fish couldn't be deleted due to it not being in database";

 }

 @Transactional
 public String addFishToWaterBody(Integer waterBodyId, Integer fishId)
 {
   // Find water body to add fish to
   Optional<WaterBody> waterBody = waterBodyRepository.findById(waterBodyId);

   if (waterBody.isPresent())
   {
      WaterBody foundWaterBody = waterBody.get();
      
      List<Fish> fishes = foundWaterBody.getFishes();

      // Find specific fish
      Optional<Fish> fish = fishRepository.findById(fishId);

      
      // If its a valid fish species
      if (fish.isPresent())
      {
        Fish foundFish = fish.get();

        List<WaterBody> waterBodies = foundFish.getWaterBodies();

        // Prevent adding same species to water body
        if (!fishes.contains(foundFish) && !waterBodies.contains(foundWaterBody))
        {
          // Adds fish to waterbody
          fishes.add(foundFish);

          // Adds water body to fish species
          waterBodies.add(foundWaterBody);
          
          // Save changes
          waterBodyRepository.save(foundWaterBody);

          fishRepository.save(foundFish);

          return  foundFish.getSpecies() + " added to " + foundWaterBody.getName();
        }
        
      }
   }

    return "Couldn't add fish to water body";
 }

 @Transactional
 public String deleteFishFromWaterBody(Integer waterBodyId, Integer fishId)
 {
   // Find water body to delete fish from
   Optional<WaterBody> waterBody = waterBodyRepository.findById(waterBodyId);

   if (waterBody.isPresent())
   {
      WaterBody foundWaterBody = waterBody.get();
      
      List<Fish> fishes = foundWaterBody.getFishes();

      // Find specific fish
      Optional<Fish> fish = fishRepository.findById(fishId);

      
      // If its a valid fish species
      if (fish.isPresent())
      {
        Fish foundFish = fish.get();

        List<WaterBody> waterBodies = foundFish.getWaterBodies();

        // Prevent deleting a wrong fish
        if (fishes.contains(foundFish) && waterBodies.contains(foundWaterBody))
        {
          // Deletes fish from waterbody
          fishes.remove(foundFish);

          // Deletes water body from fish
          waterBodies.remove(foundWaterBody);
          
          // Save changes
          waterBodyRepository.save(foundWaterBody);

          fishRepository.save(foundFish);

          return  foundFish.getSpecies() + " removed from " + foundWaterBody.getName();
        }
        
      }
   }

    return "Couldn't remove fish from water body";
 }

 @Transactional
 public String findAllFishByWaterBody(Integer wbId)
 {
   // Find Specific Water Body in database
   Optional<WaterBody> waterBodyId = waterBodyRepository.findById(wbId);

   if (waterBodyId.isPresent())
   {
      // Build a string message of all fish into a waterbody
      StringBuilder sb = new StringBuilder();

      sb.append("List of fish species for ");
    
      WaterBody foundWaterBody = waterBodyId.get();

      String name = foundWaterBody.getName(); 
      
      sb.append(name + ": ");
      
      List<Fish> list = foundWaterBody.getFishes();

      if (list.isEmpty())
      {
        return "Couldn't find any fish in " + foundWaterBody.getName();
      }

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
 public Integer getFishIdBySpecies(String species)
 {
    // Search for all fishes by its name
    List<Fish> fishes = fishRepository.findAll();

    for (Fish fish : fishes)
    {
      if (fish.getSpecies().equals(species))
      {
        return fish.getId();
      }
    }

    // Fish id wasn't found
    return 0;
 }

 @Transactional
 public String getFishInfo(Integer id)
 {
   // Find fish to make sure it exists
   Optional<Fish> fishId = fishRepository.findById(id);

   if (fishId.isPresent())
   {
      Fish foundFish = fishId.get();
    
      return foundFish.getInfo();
   }

   return "Failure due to fish not being in database";
 }

 @Transactional
 public String getFishSpecies(Integer id)
 {
   // Find fish to make sure it exists
   Optional<Fish> fishId = fishRepository.findById(id);

   if (fishId.isPresent())
   {
      Fish foundFish = fishId.get();
    
      return foundFish.getSpecies();
   }

   return "Failure due to fish not being in database";
 }

 

}
