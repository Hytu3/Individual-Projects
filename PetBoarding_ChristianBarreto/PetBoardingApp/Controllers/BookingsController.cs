using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using PetBoardingApp.Models;
using PetBoardingApp.ViewModels;
using System;
using System.Globalization;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace PetBoardingApp.Controllers
{
    public class BookingsController : Controller
    {
        // GET: Bookings
        public ActionResult Index()
        {
            var model = new BookingViewModel();
            return View(model);
        }

        // Basic CRUD Operations

        [HttpGet]
        public ActionResult Create()
        {
            return View(new BookingViewModel());
        }


        [HttpPost]
        public ActionResult Create(DateTime startTime, DateTime endTime, string petName)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Get the Pet Owner first

            // Get the current user's ID as a string
            string userIdString = User.Identity.GetUserId();

            // Convert it to a Guid 
            Guid userGuid = Guid.Parse(userIdString);

            var petOwner = dbContext.PetOwners.Find(userGuid);

            if (petOwner == null)
            {
                return Content("Error: PetOwner not found");
            }

            // Make sure owner has this pet
            var pet = dbContext.Pets.FirstOrDefault(p => p.Name == petName && p.PetOwnerID == userGuid);

            if (pet == null)
            {
                return Content("Error: You don't have a pet named " + petName);
            }


            // Create the object
            Booking booking = new Booking();
            booking.StartTime = startTime;
            booking.EndTime = endTime;
            booking.PetName = petName;
            booking.Status = "Pending";
            booking.PetOwnerID = petOwner.PetOwnerId;
            booking.PetID = pet.PetId;
            booking.Cost = 0;

            // Set check-in time and check-out time to startime and endtime
            // Employee side must update these fields
            booking.CheckInTime = startTime;
            booking.CheckOutTime = endTime;


            // Navigation properties
            booking.PetOwner = petOwner;
            booking.Pet = pet;
            booking.Employee = null;


            // Add to database
            dbContext.Bookings.Add(booking);

            try
            {
                dbContext.SaveChanges();

                return RedirectToAction("BookingSuccessful");
            }
            catch
            {
                ;
            }

            return Content("Failed");

        }

        public ActionResult BookingSuccessful()
        {
            return View();
        }

        public ActionResult Read(Guid id)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            Booking booking = dbContext.Bookings.FirstOrDefault(x => x.BookingId == id);

            // Validate and return
            if (booking == null)
            {
                return Content("No booking found with that ID");

            }

            // Return important info
            return Content("Booking ID: " + booking.BookingId + "Start Time: " + booking.StartTime + "End Time: " + booking.EndTime + "Status: " + booking.Status + "Cost: " + booking.Cost);

        }

        public ActionResult Update(Guid id, DateTime startTime, DateTime endTime, DateTime checkInTime, DateTime checkOutTime, string status, int cost)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            Booking booking = dbContext.Bookings.FirstOrDefault(x => x.BookingId == id);

            // Update properties
            booking.StartTime = startTime;
            booking.EndTime = endTime;
            booking.CheckInTime = checkInTime;
            booking.CheckOutTime = checkOutTime;
            booking.Status = status;
            booking.Cost = cost;

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
            Booking booking = dbContext.Bookings.FirstOrDefault(x => x.BookingId == id);

            if (booking != null)
            {
                
                dbContext.Bookings.Remove(booking);

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