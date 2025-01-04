package com.example.news_backend.controller;

import com.example.news_backend.model.User;
import com.example.news_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React
public class AdminController {


    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public String login(@RequestBody User admin){
        boolean isAuthenticated = adminService.login(admin.getEmail(), admin.getPassword());
        return isAuthenticated ? "login successful" : "Invalid email or password";
    }

}
