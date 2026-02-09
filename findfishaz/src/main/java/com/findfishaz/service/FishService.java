package com.findfishaz.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.findfishaz.model.Fish;
import com.findfishaz.repository.FishRepository; 

@Service
public class FishService 
{
 
 // Inject Repository

 private final FishRepository fishRepository;

 public FishService(FishRepository fishRepository) 
 {
     this.fishRepository = fishRepository;
 }

 public String create(String species, String info)
 {
    // Create new fish object
    Fish fish = new Fish();

    // Set fish fields
    fish.setSpecies(species);

    fish.setInfo(info);
  
    // Add to database
    fishRepository.save(fish);

    return "Success";
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
     
     // Change fields
     foundFish.setSpecies(species);

     foundFish.setInfo(info);

     fishRepository.save(foundFish);

     return "Update successfully";

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

 

}
