using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace PetBoardingApp.Models
{
    public class PetOwner
    {
        [Key]
        public Guid PetOwnerId { get; set;}

        [MaxLength(30)]
        public string Name { get; set; }

        [MaxLength(20)]
        public string Gender { get; set; }

        public int Age { get; set; }

        public PetOwner()
        {
            PetOwnerId = Guid.NewGuid();
        }

        // One to many relationships
        public virtual List<Pet> Pets { get; set; }

        public virtual List<Booking> Bookings { get; set; }

    }
}