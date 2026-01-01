using PetBoardingApp.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace PetBoardingApp.ViewModels
{
    public class BookingViewModel
    {
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }

        public Pet Pet { get; set; }
    }
}