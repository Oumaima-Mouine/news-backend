package com.example.news_backend.repository;

import com.example.news_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String username);
    User findFirstByUsername(String username);

}
