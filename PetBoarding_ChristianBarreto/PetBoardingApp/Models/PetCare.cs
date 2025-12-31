using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace PetBoardingApp.Models
{
    public class PetCare
    {
        [Key, ForeignKey("Pet")]
        public Guid PetID { get; set; }


        [Required]
        public TimeSpan FeedingTime { get; set; }

        [MaxLength(30)]
        public string Medicine { get; set; }

        [Required]
        public string SpecialInstructions { get; set; }

        // One to one relationship with pet
        public virtual Pet Pet { get; set; }

    }
}