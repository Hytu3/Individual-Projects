using System;
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
    public class PetCaresController : Controller
    {
        // GET: PetCares
        public ActionResult Index()
        {
            return View();
        }

        // Basic CRUD Operations
        public ActionResult Create(Guid petID, TimeSpan feedingTime, string medicine, string specialInstructions)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Get the Pet first
            var pet = dbContext.Pets.Find(petID);
            if (pet == null)
            {
                return Content("Error: Pet not found");
            }

            // Create the object
            PetCare petCare = new PetCare();
            petCare.PetID = petID;
            petCare.FeedingTime = feedingTime;
            petCare.Medicine = medicine;
            petCare.SpecialInstructions = specialInstructions;

            // Set navigation property
            petCare.Pet = pet; 

            // Add to database
            dbContext.PetCares.Add(petCare);

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
            PetCare petCare = dbContext.PetCares.FirstOrDefault(x => x.PetID == id);

            // Validate and return
            if (petCare == null)
            {
                return Content("No pet care found with that Pet ID");

            }

            // Return important info
            return Content("Pet ID: " + petCare.PetID + "Feeding Time: " + petCare.FeedingTime + "Medicine: " + petCare.Medicine + "Special Instructions: " + petCare.SpecialInstructions);

        }

        public ActionResult Update(Guid id, TimeSpan feedingTime, string medicine, string specialInstructions)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            PetCare petCare = dbContext.PetCares.FirstOrDefault(x => x.PetID == id);

            // Update properties
            petCare.FeedingTime = feedingTime;
            petCare.Medicine = medicine;
            petCare.SpecialInstructions = specialInstructions;

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

        public ActionResult Delete(Guid id)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            PetCare petCare = dbContext.PetCares.FirstOrDefault(x => x.PetID == id);

            if (petCare != null)
            {
                dbContext.PetCares.Remove(petCare);

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