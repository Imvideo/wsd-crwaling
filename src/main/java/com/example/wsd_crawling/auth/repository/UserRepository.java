package com.example.wsd_crawling.auth.repository;

import com.example.wsd_crawling.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
