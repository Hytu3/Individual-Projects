namespace PetBoardingApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ChangedBookingModel2 : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.Bookings", name: "PetID", newName: "Pet_PetId");
            RenameIndex(table: "dbo.Bookings", name: "IX_PetID", newName: "IX_Pet_PetId");
            AddColumn("dbo.Bookings", "PetName", c => c.String(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Bookings", "PetName");
            RenameIndex(table: "dbo.Bookings", name: "IX_Pet_PetId", newName: "IX_PetID");
            RenameColumn(table: "dbo.Bookings", name: "Pet_PetId", newName: "PetID");
        }
    }
}
