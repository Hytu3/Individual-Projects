namespace PetBoardingApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangedBookingModel5 : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.Bookings", name: "Pet_PetId", newName: "PetID");
            RenameIndex(table: "dbo.Bookings", name: "IX_Pet_PetId", newName: "IX_PetID");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.Bookings", name: "IX_PetID", newName: "IX_Pet_PetId");
            RenameColumn(table: "dbo.Bookings", name: "PetID", newName: "Pet_PetId");
        }
    }
}
