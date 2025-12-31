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
using WebAppTemplate.Models;

namespace WebAppTemplate.Controllers
{
    public class AdminController : Controller
    {
        // GET: Admin
        public ActionResult Index()
        {
            return View();
        }

        // GET: Admin/Pets
        public ActionResult Pets()
        {
            return View();
        }

        // GET: Admin/Bookings
        public ActionResult Bookings()
        {
            return View();
        }
    }
}