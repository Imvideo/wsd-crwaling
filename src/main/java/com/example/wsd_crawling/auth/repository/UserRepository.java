package com.example.wsd_crawling.auth.repository;

import com.example.wsd_crawling.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 필요한 추가 메서드 정의
}
