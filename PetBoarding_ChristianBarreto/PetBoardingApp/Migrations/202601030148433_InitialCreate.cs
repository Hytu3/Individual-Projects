namespace PetBoardingApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Bookings",
                c => new
                    {
                        BookingId = c.Guid(nullable: false),
                        StartTime = c.DateTime(nullable: false),
                        EndTime = c.DateTime(nullable: false),
                        CheckInTime = c.DateTime(nullable: false),
                        CheckOutTime = c.DateTime(nullable: false),
                        Status = c.String(nullable: false),
                        Cost = c.Int(nullable: false),
                        PetOwnerID = c.Guid(nullable: false),
                        EmployeeID = c.Guid(nullable: false),
                        PetID = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.BookingId)
                .ForeignKey("dbo.Employees", t => t.EmployeeID)
                .ForeignKey("dbo.Pets", t => t.PetID)
                .ForeignKey("dbo.PetOwners", t => t.PetOwnerID)
                .Index(t => t.PetOwnerID)
                .Index(t => t.EmployeeID)
                .Index(t => t.PetID);
            
            CreateTable(
                "dbo.Employees",
                c => new
                    {
                        EmployeeId = c.Guid(nullable: false),
                        Status = c.String(maxLength: 30),
                        IsActive = c.Boolean(nullable: false),
                        Name = c.String(nullable: false, maxLength: 30),
                    })
                .PrimaryKey(t => t.EmployeeId);
            
            CreateTable(
                "dbo.Pets",
                c => new
                    {
                        PetId = c.Guid(nullable: false),
                        Name = c.String(nullable: false, maxLength: 30),
                        Breed = c.String(nullable: false, maxLength: 30),
                        Age = c.Int(nullable: false),
                        PetOwnerID = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.PetId)
                .ForeignKey("dbo.PetOwners", t => t.PetOwnerID)
                .Index(t => t.PetOwnerID);
            
            CreateTable(
                "dbo.PetCares",
                c => new
                    {
                        PetID = c.Guid(nullable: false),
                        FeedingTime = c.Time(nullable: false, precision: 7),
                        Medicine = c.String(maxLength: 30),
                        SpecialInstructions = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.PetID)
                .ForeignKey("dbo.Pets", t => t.PetID)
                .Index(t => t.PetID);
            
            CreateTable(
                "dbo.PetOwners",
                c => new
                    {
                        PetOwnerId = c.Guid(nullable: false),
                        Name = c.String(maxLength: 30),
                        Gender = c.String(maxLength: 20),
                        Age = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.PetOwnerId);
            
            CreateTable(
                "dbo.ContactUs",
                c => new
                    {
                        ContactUsId = c.Guid(nullable: false),
                        Email = c.String(nullable: false, maxLength: 40),
                        Phone_number = c.String(nullable: false, maxLength: 20),
                        Address = c.String(maxLength: 40),
                        Message = c.String(maxLength: 300),
                    })
                .PrimaryKey(t => t.ContactUsId);
            
            CreateTable(
                "dbo.AspNetRoles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
            CreateTable(
                "dbo.AspNetUserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        RoleId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        RecoveryEmail = c.String(),
                        Name = c.String(),
                        Gender = c.String(),
                        Age = c.Int(nullable: false),
                        Email = c.String(maxLength: 256),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.Bookings", "PetOwnerID", "dbo.PetOwners");
            DropForeignKey("dbo.Bookings", "PetID", "dbo.Pets");
            DropForeignKey("dbo.Pets", "PetOwnerID", "dbo.PetOwners");
            DropForeignKey("dbo.PetCares", "PetID", "dbo.Pets");
            DropForeignKey("dbo.Bookings", "EmployeeID", "dbo.Employees");
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetRoles", "RoleNameIndex");
            DropIndex("dbo.PetCares", new[] { "PetID" });
            DropIndex("dbo.Pets", new[] { "PetOwnerID" });
            DropIndex("dbo.Bookings", new[] { "PetID" });
            DropIndex("dbo.Bookings", new[] { "EmployeeID" });
            DropIndex("dbo.Bookings", new[] { "PetOwnerID" });
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.ContactUs");
            DropTable("dbo.PetOwners");
            DropTable("dbo.PetCares");
            DropTable("dbo.Pets");
            DropTable("dbo.Employees");
            DropTable("dbo.Bookings");
        }
    }
}
