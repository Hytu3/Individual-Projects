using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace PetBoardingApp.Models
{
    public class ContactUs
    {
        public Guid ContactUsId { get; set; }


        [MaxLength(40)]
        [Required]
        public string Email { get; set; }

        [MaxLength(20)]
        [Required]
        public string Phone_number { get; set; }

        [MaxLength (40)]
        public string Address { get; set; }

        [MaxLength(300)]
        public string Message { get; set; }

        public ContactUs()
        {
            ContactUsId = Guid.NewGuid();
        }

    }
}