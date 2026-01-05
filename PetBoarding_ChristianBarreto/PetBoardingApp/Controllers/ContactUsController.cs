using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using System;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using PetBoardingApp.Models;
using PetBoardingApp.ViewModels;

namespace PetBoardingApp.Controllers
{
    public class ContactUsController : Controller
    {
        // GET: ContactUs
        public ActionResult Index()
        {
            return View();
        }

        // Basic CRUD Operations
        
        [HttpGet]
        public ActionResult Create()
        {
            return View(new ContactUsViewModel());
        }


        [HttpPost]
        public ActionResult Create(ContactUsViewModel contactUsVM)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Create the object
            ContactUs contactUs = new ContactUs();
            contactUs.Email = contactUsVM.Email;
            contactUs.Phone_number = contactUsVM.Phone_number;
            contactUs.Address = contactUsVM.Address;
            contactUs.Message = contactUsVM.Message;
            
            // Add to database
            dbContext.ContactUs.Add(contactUs);

            try
            {
                dbContext.SaveChanges();
                // If it is successful
                return RedirectToAction("ContactUsSuccessful");
            }
            catch
            {
                ;
            }

            return Content("Failed");

        }

        public ActionResult ContactUsSuccessful()
        {
            return View();
        }


        public ActionResult Read(Guid id)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            ContactUs contactUs = dbContext.ContactUs.FirstOrDefault(x => x.ContactUsId == id);

            // Validate and return
            if (contactUs == null)
            {
                return Content("No contact info found with that Contact Us ID");
            }

            // Return important info
            return Content("Contact US ID: " + contactUs.ContactUsId + "Email: " + contactUs.Email + "Phone: " + contactUs.Phone_number + "Address: " + contactUs.Address + "Message: " + contactUs.Message);

        }

        public ActionResult Update(Guid id, string email, string phone_number, string address, string message)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            ContactUs contactUs = dbContext.ContactUs.FirstOrDefault(x => x.ContactUsId == id);

            // Update properties
            contactUs.Email = email;
            contactUs.Phone_number = phone_number;
            contactUs.Address = address;
            contactUs.Message = message;

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
            ContactUs contactUs = dbContext.ContactUs.FirstOrDefault(x => x.ContactUsId == id);

            if (contactUs != null)
            {
                dbContext.ContactUs.Remove(contactUs);

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