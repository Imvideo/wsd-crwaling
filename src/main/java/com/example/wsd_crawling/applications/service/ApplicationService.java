package com.example.wsd_crawling.applications.service;

import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public void apply(Long userId, Long jobPostingId, String status) {
        // 중복 지원 체크
        if (applicationRepository.findByUserIdAndJobPostingId(userId, jobPostingId).isPresent()) {
            throw new IllegalStateException("이미 해당 공고에 지원하셨습니다.");
        }

        // 지원 상태가 유효한지 확인
        if (!status.equalsIgnoreCase("PENDING") && !status.equalsIgnoreCase("APPLIED")) {
            throw new IllegalStateException("유효하지 않은 지원 상태입니다.");
        }

        // 지원 정보 저장
        Application application = new Application();
        application.setUserId(userId);
        application.setJobPostingId(jobPostingId);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus(status.toUpperCase()); // 상태를 대문자로 저장

        applicationRepository.save(application);
    }


    // 사용자별 지원 목록 조회
    public Page<Application> getApplicationsByUser(Long userId, String status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            return applicationRepository.findByUserId(userId, pageable); // 상태 필터링 X
        } else {
            return applicationRepository.findByUserIdAndStatus(userId, status, pageable); // 상태 필터링 O
        }
    }

    // 지원 취소 메서드
    public boolean deletePendingApplicationsByUserId(Long userId) {
        // 모든 PENDING 상태 지원 조회
        List<Application> pendingApplications = applicationRepository.findAllByUserIdAndStatus(userId, "PENDING");

        if (pendingApplications.isEmpty()) {
            return false; // 삭제할 항목 없음
        }

        // 지원 삭제
        applicationRepository.deleteAll(pendingApplications);
        return true;
    }

    // 페이징 처리를 위한 지원 목록 조회
    public Page<Application> getApplicationsByStatus(Long userId, String status, Pageable pageable) {
        return applicationRepository.findByUserIdAndStatus(userId, status, pageable);
    }




}
