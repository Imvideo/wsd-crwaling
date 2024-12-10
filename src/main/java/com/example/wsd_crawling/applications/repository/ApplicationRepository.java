package com.example.wsd_crawling.applications.repository;

import com.example.wsd_crawling.applications.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserIdAndJobPostingId(Long userId, Long jobPostingId);
}
