using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PetBoardingApp.Models
{
    public class Employee
    {
        [Key]
        public Guid EmployeeId { get; set; }

        [MaxLength(30)]
        public string Status { get; set; }

        
        public Boolean IsActive { get; set; }

        [Required]
        [MaxLength(30)]
        public string Name { get; set; }

        // One to one many with bookings
        public virtual List<Booking> Bookings { get; set; }

        public Employee()
        {
            EmployeeId = Guid.NewGuid();
        }
    }
}