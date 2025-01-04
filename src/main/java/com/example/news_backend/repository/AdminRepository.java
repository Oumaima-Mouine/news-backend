package com.example.news_backend.repository;

import com.example.news_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
