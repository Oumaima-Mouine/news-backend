package com.example.news_backend.controller;

import com.example.news_backend.dto.LoginDTO;
import com.example.news_backend.model.User;
import com.example.news_backend.service.AdminService;
import com.example.news_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            boolean isAuthenticated = adminService.login(loginDTO.getEmail(), loginDTO.getPassword());

            if (isAuthenticated) {
                UserDetails userDetails = adminService.loadUserByUsername(loginDTO.getEmail());
                String token = jwtUtil.generateToken(userDetails);

                // Fetch additional user details
                User user = adminService.getUserByEmail(loginDTO.getEmail());
                String role = String.valueOf(user.getRole()); // Assuming "role" is a field in User
                String email = user.getEmail();

                // Prepare response
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("token", token);
                response.put("role", role);
                response.put("email", email);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred"));
        }
    }

    @GetMapping("/hash")
    public String hashPassword(@RequestParam String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}