using System;
using System.Globalization;
using System.Linq;
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
    public class BookingsController : Controller
    {
        // GET: Bookings
        public ActionResult Index()
        {
            return View();
        }

        // Basic CRUD Operations
        public ActionResult Create(DateTime startTime, DateTime endTime, DateTime checkInTime, DateTime checkOutTime, string status, int cost, Guid petOwnerID, Guid petID, Guid employeeID)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Get the Pet Owner first
            var petOwner = dbContext.PetOwners.Find(petOwnerID);
            
            if (petOwner == null)
            {
                return Content("Error: PetOwner not found");
            }

            // Get the Pet first
            var pet = dbContext.Pets.Find(petID);

            if (pet == null)
            {
                return Content("Error: Pet not found");
            }

            // Get the Employee first
            var employee = dbContext.Employees.Find(employeeID);

            if (employee == null)
            {
                return Content("Error: Employee not found");
            }


            // Create the object
            Booking booking = new Booking();
            booking.StartTime = startTime;
            booking.EndTime = endTime;
            booking.CheckInTime = checkInTime;
            booking.CheckOutTime = checkOutTime;
            booking.Status = status;
            booking.Cost = cost;
            booking.PetOwnerID = petOwnerID;
            booking.PetID = petID;
            booking.EmployeeID = employeeID;

            // Navigation properties
            booking.PetOwner = petOwner;
            booking.Pet = pet;
            booking.Employee = employee;

            // Add to database
            dbContext.Bookings.Add(booking);

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