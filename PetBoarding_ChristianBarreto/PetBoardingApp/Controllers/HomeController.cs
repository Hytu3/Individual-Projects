using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace PetBoardingApp.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "The application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "My contact page.";

            return View();
        }

        public ActionResult GitBasics()
        {
            return Content("Git Basics");
        }

        public ActionResult EndpointA()
        {
            return Content("This is endpoint A");
        }

        // CRUD Routes

        public ActionResult Create()
        {
            return Content("Create");
        }

        public ActionResult Read()
        {
            return Content("Read");
        }

        public ActionResult Update()
        {
            return Content("Update");
        }

        public ActionResult Delete()
        {
            return Content("Delete");
        }
    }
}