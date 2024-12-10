package com.example.wsd_crawling.applications.repository;

import com.example.wsd_crawling.applications.model.Application;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUserIdAndJobPostingId(Long userId, Long jobPostingId);

    // 사용자별 지원 목록 조회
    Page<Application> findByUserId(Long userId, Pageable pageable);

    // 상태별 필터링
    Page<Application> findByUserIdAndStatus(Long userId, String status, Pageable pageable);

    List<Application> findAllByUserIdAndStatus(Long userId, String status);

}
