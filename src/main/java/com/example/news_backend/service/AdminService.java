package com.example.news_backend.service;

import com.example.news_backend.model.User;
import com.example.news_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository adminRepository;

    public boolean login(String email, String password) {
        Optional<User> admin = Optional.ofNullable(adminRepository.findFirstByEmail(email));
        if (admin.isPresent()) {
            System.out.println("User found: " + admin.get().getEmail());
            return admin.get().getPassword().equals(password);
        } else {
            System.out.println("User not found with email: " + email);
            return false;
        }
    }
}