
    using Microsoft.AspNet.Identity;
    using Microsoft.AspNet.Identity.Owin;
    using Microsoft.Owin.Security;
    using PetBoardingApp.Models;
    using PetBoardingApp.ViewModels;
﻿    using System;
    using System.Collections.Generic;
    using System.Globalization;
    using System.Linq;
    using System.Net.Mime;
    using System.Security.Claims;
    using System.Threading.Tasks;
    using System.Web;
    using System.Web.Mvc;

    namespace PetBoardingApp.Controllers
    {
        public class PetsController : Controller
        {
            // GET: Pets
            public ActionResult Index()
            {
                var model = new PetViewModel();
                return View(model);
            }

            // Basic CRUD Operations

            [HttpGet]
            public ActionResult Create()
            {
                return View(new PetViewModel());
            }


            [HttpPost]    
            public ActionResult Create(string name, string breed, int age, Guid petOwnerID)
            {
                // Connect to database
                ApplicationDbContext dbContext = new ApplicationDbContext();

                // Get the Pet Owner first
                var petOwner = dbContext.PetOwners.Find(petOwnerID);
            
                if (petOwner == null)
                {
                    return Content("Error: PetOwner not found");
                }

                // Create the object
                Pet pet = new Pet();
                pet.Name = name;
                pet.Breed = breed;
                pet.Age = age;
                pet.PetOwnerID = petOwnerID;
            
                // Navigation property
                pet.PetOwner = petOwner; 

                // Add to database
                dbContext.Pets.Add(pet);

                try
                {
                    dbContext.SaveChanges();
                }
                catch
                {
                    ;
                }

                return Content("Create");
            }


            public ActionResult Read(Guid id) 
            {
                // Connect to database
                ApplicationDbContext dbContext = new ApplicationDbContext();

                // Query the database
                Pet pet = dbContext.Pets.FirstOrDefault(x => x.PetId == id); 

                // Validate and return
                if (pet == null) // Fixed condition logic
                {
                    return Content("No pet found with that ID");

                }

                // Return important info
                return Content("Pet ID: " + pet.PetId + "Name: " + pet.Name + "Breed: " + pet.Breed + "Age: " + pet.Age); 

            }

            public ActionResult Update(Guid id, string name, string breed, int age) 
            {
                // Connect to database
                ApplicationDbContext dbContext = new ApplicationDbContext();

                // Query the database
                Pet pet = dbContext.Pets.FirstOrDefault(x => x.PetId == id); 

                // Update age
                pet.Age = age; 

                try
                {
                    dbContext.SaveChanges();
                }
                catch
                {
                    ;
                }

                return Content("Update");
            }

            [HttpPost]
            public ActionResult Delete(Guid id) 
            {
                // Connect to database
                ApplicationDbContext dbContext = new ApplicationDbContext();

                // Query the database
                Pet pet = dbContext.Pets.FirstOrDefault(x => x.PetId == id); 

                if (pet != null)
                {

                    // Prevent from bookings being orphaned
                    List<Booking> bookings = pet.Bookings.ToList();

                    foreach (var booking in bookings)
                    {
                        dbContext.Bookings.Remove(booking);

                    }

                    // If Pet Owner is removed remove the pet care form

                    var petcareForm = pet.PetCare;

                    dbContext.PetCares.Remove(petcareForm);



                // Remove from database
                dbContext.Pets.Remove(pet);

                    try
                    {
                        dbContext.SaveChanges();
                    }
                    catch
                    {
                        ;
                    }

                }


                return Content("Delete");
            }
        }
    }
    
