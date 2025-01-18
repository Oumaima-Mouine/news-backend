package com.example.news_backend.service;

import com.example.news_backend.model.User;
import com.example.news_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean login(String email, String rawPassword) {
        Optional<User> admin = Optional.ofNullable(userRepository.findFirstByEmail(email));
        if (admin.isPresent()) {
            String storedPasswordHash = admin.get().getPassword();
            return passwordEncoder.matches(rawPassword, storedPasswordHash);
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public User getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }
}