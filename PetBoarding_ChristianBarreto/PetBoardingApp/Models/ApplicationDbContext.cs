using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace PetBoardingApp.Models
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public DbSet<Pet> Pets { get; set; }

        public DbSet<PetCare> PetCares { get; set; }

        public DbSet<PetOwner> PetOwners { get; set; }

        public DbSet<Booking> Bookings { get; set; }

        public DbSet<Employee> Employees { get; set; }

        public DbSet<ContactUs> ContactUs { get; set; }



        public ApplicationDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {

        }

        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }

        // AI Generated method to ensure I delete entities manually
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // PetOwner -> Pets: NO CASCADE
            modelBuilder.Entity<Pet>()
                .HasRequired(p => p.PetOwner)
                .WithMany(o => o.Pets)
                .HasForeignKey(p => p.PetOwnerID)
                .WillCascadeOnDelete(false);

            // Pet -> Bookings: NO CASCADE
            modelBuilder.Entity<Booking>()
                .HasRequired(b => b.Pet)
                .WithMany(p => p.Bookings)
                .WillCascadeOnDelete(false);

            // UPDATED: Employee -> Bookings (Changed to Optional)
            modelBuilder.Entity<Booking>()
                .HasOptional(b => b.Employee) // This allows NULL in the database
                .WithMany(e => e.Bookings)
                .HasForeignKey(b => b.EmployeeID) // Ensure this property exists in Booking.cs as Guid?
                .WillCascadeOnDelete(false);

            // PetOwner -> Bookings: NO CASCADE
            modelBuilder.Entity<Booking>()
                .HasRequired(b => b.PetOwner)
                .WithMany(o => o.Bookings)
                .HasForeignKey(b => b.PetOwnerID)
                .WillCascadeOnDelete(false);
        }
    }
}