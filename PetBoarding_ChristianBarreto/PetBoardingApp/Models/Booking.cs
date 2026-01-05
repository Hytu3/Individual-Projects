using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace PetBoardingApp.Models
{
    public class Booking
    {
        [Key]
        public Guid BookingId { get; set; }

        
        [Required]
        public DateTime StartTime { get; set; }

        [Required]
        public DateTime EndTime { get; set; }

        [Required]
        public string PetName { get; set; }

        
        public DateTime CheckInTime { get; set; }

        public DateTime CheckOutTime { get; set; }

        public string Status { get; set; }

        public int Cost { get; set; }

        [Required]
        [ForeignKey("PetOwner")]
        public Guid PetOwnerID { get; set; }

        [Required]
        [ForeignKey("Pet")]
        public Guid PetID { get; set; }

        public Guid? EmployeeID { get; set; }

        // Petowner is the parent
        // One to many relationship


        public virtual PetOwner PetOwner { get; set; }

        // Employee is the parent
        // One to many relationship

        
        public virtual Employee Employee { get; set; }

        // Pet is the parent
        // One to many relationship

        [Required]
        public virtual Pet Pet { get; set; }


        public Booking()
        {
            BookingId = Guid.NewGuid();
        }
    }
}