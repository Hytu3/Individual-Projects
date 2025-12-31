using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace PetBoardingApp.Models
{
    public class Pet
    {
        [Key]
        public Guid PetId { get; set; }

        [MaxLength(30)]
        [Required]
        public string Name { get; set; }

        [MaxLength(30)]
        [Required]  
        public string Breed { get; set; }

        [Required]
        public int Age { get; set; }

        [Required]
        [ForeignKey("PetOwner")]  
        public Guid PetOwnerID { get; set; }

        // Petowner is the parent
        [Required]
        public virtual PetOwner PetOwner { get; set; }

        // One to one relationship with petcare
        public virtual PetCare PetCare { get; set; }

        // One to one many with bookings
        public virtual List<Booking> Bookings { get; set; }

        public Pet ()
        {
            PetId = Guid.NewGuid();
        }

    }
}