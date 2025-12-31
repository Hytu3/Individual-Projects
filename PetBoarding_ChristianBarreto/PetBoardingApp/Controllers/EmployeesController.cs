using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using PetBoardingApp.Models;

namespace PetBoardingApp.Controllers
{
    public class EmployeesController : Controller
    {
        // GET: Employees
        public ActionResult Index()
        {
            return View();
        }

        // Basic CRUD Operations
        public ActionResult Create(string status, bool isActive, string name)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Create the object
            Employee employee = new Employee();
            employee.Status = status;
            employee.IsActive = isActive;
            employee.Name = name;

            // Add to database
            dbContext.Employees.Add(employee);

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
            Employee employee = dbContext.Employees.FirstOrDefault(x => x.EmployeeId == id);

            // Validate and return
            if (employee == null)
            {
                return Content("No employee found with that ID");

            }

            // Return important info
            return Content("Employee ID: " + employee.EmployeeId + "Name: " + employee.Name + "Status: " + employee.Status + "Is Active: " + employee.IsActive);

        }

        public ActionResult Update(Guid id, string status, bool isActive, string name)
        {
            // Connect to database
            ApplicationDbContext dbContext = new ApplicationDbContext();

            // Query the database
            Employee employee = dbContext.Employees.FirstOrDefault(x => x.EmployeeId == id);

            // Update properties
            employee.Status = status;
            employee.IsActive = isActive;
            employee.Name = name;

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
            Employee employee = dbContext.Employees.FirstOrDefault(x => x.EmployeeId == id);

            if (employee != null)
            {
                // Prevent from being orphaned
                List<Booking> bookings = employee.Bookings.ToList();

                foreach (var booking in bookings)
                {
                    dbContext.Bookings.Remove(booking);

                }


                dbContext.Employees.Remove(employee);

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
