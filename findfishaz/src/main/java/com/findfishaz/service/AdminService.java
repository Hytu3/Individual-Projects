package com.findfishaz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.findfishaz.model.Admin;
import com.findfishaz.repository.AdminRepository;

@Service
public class AdminService 
{
 
 // Inject Repository
  private final AdminRepository adminRepository;
  
  @Autowired
  public AdminService(AdminRepository adminRepository) 
  {
      this.adminRepository = adminRepository;
  }

  public Boolean login(String email, String password)
  {
    List<Admin> admins = adminRepository.findAll();

    for (Admin admin : admins)
    {
      if (admin.getEmail().equals(email) && admin.getPassword().equals(password))
      {
        return true;
      }
    }

    return false;
  }
  
  
  public String create(String name, String email, String password)
  {
      // Prevent duplicate creations
      // See if admin already exists in the database
      Integer adminId = getAdminIdByName(name);

      if (adminId != null)
      {
          Optional<Admin> foundAdmin = adminRepository.findById(adminId);

          if (foundAdmin.isPresent())
          {
              return "Admin already exists!";
          }
      }
        
      // Create new admin object
      Admin admin = new Admin();

      // Set admin fields
      admin.setName(name);
      admin.setEmail(email);
      admin.setPassword(password); // Reminder: Hash this later for security
    
      // Add to database
      adminRepository.save(admin);

      return "Admin Created";
  }

  public String read(Integer id)
  {
    // Find admin to make sure it exists
    Optional<Admin> adminId = adminRepository.findById(id);

    if (adminId.isPresent())
    {
        Admin foundAdmin = adminId.get();
      
        return "Admin name: " + foundAdmin.getName() + " Admin email: " + foundAdmin.getEmail();
    }

    return "Failure due to admin not being in database";
  }

  public String update(Integer id, String email, String password)
  {
    // Find admin to make sure it exists
    Optional<Admin> adminId = adminRepository.findById(id);

    if (adminId.isPresent())
    {
      Admin foundAdmin = adminId.get();
      
      // Update info
      foundAdmin.setEmail(email);
      foundAdmin.setPassword(password);

      adminRepository.save(foundAdmin);

      return "Updated info successfully";
    }

    return "Failure to update as admin is not in database";
  }

  public String delete(Integer id)
  {
    // Find admin to make sure it exists
    Boolean ifExists = adminRepository.existsById(id);

    if (ifExists)
    {
      adminRepository.deleteById(id);

      return "Admin deleted from database";
    }

    return "Admin couldn't be deleted due to it not being in database";
  }

  public Integer getAdminIdByName(String name)
  {
    // Assuming adminRepository has a method to return all, matching your logic
    List<Admin> admins = adminRepository.findAll();

    for (Admin admin : admins)
    {
      if (admin.getName().equals(name))
      {
        return admin.getId();
      }
    }

    
    return null; 
  }
}