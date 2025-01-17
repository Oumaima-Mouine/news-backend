package com.example.news_backend.service;

import com.example.news_backend.model.User;
import com.example.news_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository adminRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean login(String email, String rawPassword) {
        Optional<User> admin = Optional.ofNullable(adminRepository.findFirstByEmail(email));
        if (admin.isPresent()) {
            String storedPasswordHash = admin.get().getPassword();
            System.out.println("User found: " + admin.get().getEmail());
            System.out.println("Database Password Hash: " + storedPasswordHash);

            // Compare the raw password with the encoded password
            boolean passwordMatches = passwordEncoder.matches(rawPassword, storedPasswordHash);
            System.out.println("Password Matches: " + passwordMatches);

            return passwordMatches;
        } else {
            System.out.println("User not found with email: " + email);
            return false;
        }
    }
}
