package com.findfishaz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.findfishaz.service.AdminService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController
{
   private final AdminService adminService;

   @Autowired
   public AdminController(AdminService adminService)
   {
    this.adminService = adminService;
   }

   @GetMapping("/showLoginPage")
   public String showLoginPage()
   {
     return "loginPage";
   }

   @PostMapping("/login")
   public String login(HttpSession session, @RequestParam("Email") String email, @RequestParam("Password") String password)
   {
     Boolean result = adminService.login(email,password);

     if (result)
     {
        session.setAttribute("userAuthenticated", email);
        return "loginSuccess";
     }

     return "loginFail";
   }
   
   @PostMapping("/create")
   public String create(Model model, String name, String email, String password)
   {
      String result = adminService.create(name,email,password);
      
      model.addAttribute("createMessage", result);

      return "createAdmin";
   }

   @GetMapping("/read")
   public String read(Model model, String name)
   {
     Integer id = adminService.getAdminIdByName(name);
    
     String result = adminService.read(id);
      
     model.addAttribute("readMessage", result);

     return "readAdmin";
   }

   @PutMapping("/update")
   public String update(Model model, String name, String email, String password)
   {
     Integer id = adminService.getAdminIdByName(name);
    
     String result = adminService.update(id, email, password);
      
     model.addAttribute("updateMessage", result);

     return "updateAdmin";
   }

   @DeleteMapping("/delete")
   public String delete(Model model, String name)
   {
     Integer id = adminService.getAdminIdByName(name);
    
     String result = adminService.delete(id);
      
     model.addAttribute("deleteMessage", result);

     return "deleteAdmin";
   }
}