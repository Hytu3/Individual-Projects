using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using PetBoardingApp.Models;

namespace PetBoardingApp.ViewModels
{
    public class ContactUsViewModel
    {
        public string Email { get; set; }

        public string Phone_number { get; set; }

        public string Address { get; set; }

        public string Message { get; set; }

    }
}