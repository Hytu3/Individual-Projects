using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net.Mime;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using PetBoardingApp.Models;

namespace PetBoardingApp.Controllers
{
    public class PetOwnersController : Controller
    {
        // GET: PetOwners
        public ActionResult Index()
        {
            return View();
        }

        // Basic CRUD Operations
        public ActionResult Create(string name, string gender, int age)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Create the object
            PetOwner petOwner = new PetOwner();
            petOwner.Name = name;
            petOwner.Gender = gender;
            petOwner.Age = age;

            // Add to database
            dbContext.PetOwners.Add(petOwner);

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
            PetOwner petOwner = dbContext.PetOwners.FirstOrDefault(x => x.PetOwnerId == id);

            // Validate and return
            if (petOwner == null)
            {
                return Content("No pet owner found with that ID");

            }

            // Return important info
            return Content("Pet Owner ID: " + petOwner.PetOwnerId + "Name: " + petOwner.Name + "Gender: " + petOwner.Gender + "Age: " + petOwner.Age);

        }

        public ActionResult Update(Guid id, string name, string gender, int age)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            PetOwner petOwner = dbContext.PetOwners.FirstOrDefault(x => x.PetOwnerId == id);

            // Update properties
            petOwner.Name = name;
            petOwner.Gender = gender;
            petOwner.Age = age;

            try
            {
                dbContext.SaveChanges();
            }
            catch
            {
                return Content("Exception thrown!");
            }

            return Content("Update");
        }

        public ActionResult Delete(Guid id)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            PetOwner petOwner = dbContext.PetOwners.FirstOrDefault(x => x.PetOwnerId == id);

            if (petOwner != null)
            {

                // Prevent pets from being orphaned
                List<Pet> pets = petOwner.Pets.ToList();

                foreach (var pet in pets)
                {
                    dbContext.Pets.Remove(pet);  

                }

                // Prevent bookings from being orphaned
                List<Booking> bookings = petOwner.Bookings.ToList();

                foreach (var booking in bookings)
                {
                    dbContext.Bookings.Remove(booking);

                }

                dbContext.PetOwners.Remove(petOwner);

                try
                {
                    dbContext.SaveChanges();
                }
                catch (Exception ex)
                {
                    return Content($"Exception Type: {ex.GetType().FullName}");
                    
                }

            }


            return Content("Delete");
        }
    }
}