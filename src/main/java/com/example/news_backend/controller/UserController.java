package com.example.news_backend.controller;

import com.example.news_backend.model.User;
import com.example.news_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        return adminService.login(user.getEmail(), user.getPassword());
    }
}