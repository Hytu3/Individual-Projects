namespace PetBoardingApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangedBookingModel7 : DbMigration
    {
        public override void Up()
        {
            DropIndex("dbo.Bookings", new[] { "EmployeeID" });
            AlterColumn("dbo.Bookings", "EmployeeID", c => c.Guid());
            CreateIndex("dbo.Bookings", "EmployeeID");
        }
        
        public override void Down()
        {
            DropIndex("dbo.Bookings", new[] { "EmployeeID" });
            AlterColumn("dbo.Bookings", "EmployeeID", c => c.Guid(nullable: false));
            CreateIndex("dbo.Bookings", "EmployeeID");
        }
    }
}
